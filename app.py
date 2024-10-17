from flask import Flask, render_template, request, jsonify
import subprocess
import threading
import queue

app = Flask(__name__)

import time

def run_aider(prompt, result_queue):
    try:
        start_time = time.time()
        process = subprocess.Popen(['aider', '--no-pretty', '--no-interactive', '--sonnet', prompt], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
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
            result['error'] = f'Aider process failed with return code {return_code}'

        result_queue.put(result)
    except Exception as e:
        result_queue.put({'error': f'Error running aider: {str(e)}'})

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        prompt = request.form['query']
        result_queue = queue.Queue()
        thread = threading.Thread(target=run_aider, args=(prompt, result_queue))
        thread.start()
        thread.join(timeout=60)  # Wait for up to 60 seconds

        if thread.is_alive():
            return jsonify({'error': 'Operation timed out'}), 408

        result = result_queue.get()
        if 'error' in result:
            return jsonify({'error': result['error']}), 400
        
        response = {
            'result': result.get('stdout', ''),
            'error': result.get('stderr', ''),
            'return_code': result.get('return_code', None),
            'execution_time': result.get('execution_time', None)
        }
        return jsonify(response)

    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
