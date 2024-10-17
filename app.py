from flask import Flask, render_template, request, jsonify
import subprocess
import threading
import queue
import time
import logging
import shutil

app = Flask(__name__)
logging.basicConfig(level=logging.DEBUG)

def check_aider_installation():
    return shutil.which('aider') is not None

def run_aider(prompt, result_queue):
    if not check_aider_installation():
        result_queue.put({'error': 'Aider is not installed or not in the system PATH'})
        return

    try:
        start_time = time.time()
        command = ['aider', '--no-pretty', '--sonnet', prompt]
        logging.debug(f"Running command: {' '.join(command)}")
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        logging.debug("Subprocess started")
        try:
            stdout, stderr = process.communicate(timeout=55)  # 55 seconds timeout
        except subprocess.TimeoutExpired:
            process.kill()
            stdout, stderr = process.communicate()
            result_queue.put({'error': 'Aider process timed out'})
            return

        end_time = time.time()
        execution_time = end_time - start_time
        return_code = process.returncode

        result = {
            'stdout': stdout,
            'stderr': stderr,
            'return_code': return_code,
            'execution_time': execution_time
        }

        if return_code != 0:
            result['error'] = f'Aider process failed with return code {return_code}. Stderr: {stderr}'
        
        logging.debug(f"Aider result: {result}")
        result_queue.put(result)
    except Exception as e:
        logging.exception("Error running aider")
        result_queue.put({'error': f'Error running aider: {str(e)}'})

@app.route('/', methods=['GET', 'POST'])
def index():
    logging.info(f"Received {request.method} request")
    if request.method == 'POST':
        prompt = request.form['query']
        logging.info(f"Received prompt: {prompt}")
        result_queue = queue.Queue()
        thread = threading.Thread(target=run_aider, args=(prompt, result_queue))
        thread.start()
        thread.join(timeout=60)  # Wait for up to 60 seconds

        if thread.is_alive():
            logging.warning("Operation timed out")
            return jsonify({'error': 'Operation timed out'}), 408

        result = result_queue.get()
        if 'error' in result:
            logging.error(f"Error in result: {result['error']}")
            return jsonify({'error': result['error']}), 400
        
        response = {
            'result': result.get('stdout', ''),
            'error': result.get('stderr', ''),
            'return_code': result.get('return_code', None),
            'execution_time': result.get('execution_time', None)
        }
        logging.info(f"Sending response: {response}")
        return jsonify(response)

    return render_template('index.html')

if __name__ == '__main__':
    logging.info("Starting Flask app...")
    app.run(debug=True, host='0.0.0.0')
