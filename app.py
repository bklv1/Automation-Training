from flask import Flask, render_template, request, jsonify
import subprocess
import threading
import queue

app = Flask(__name__)

def run_aider(prompt, result_queue):
    try:
        process = subprocess.Popen(['aider', '--no-pretty', '--no-interactive', '--sonnet', prompt], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        stdout, stderr = process.communicate()
        result_queue.put({'stdout': stdout, 'stderr': stderr})
    except Exception as e:
        result_queue.put({'error': str(e)})

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
        return jsonify({'result': result['stdout'], 'error': result['stderr']})

    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
