# app.py
from flask import Flask, request, jsonify, render_template
import subprocess
import os
import sys

app = Flask(__name__)

def run_python_script():
    cwd = os.getcwd()
    test_recorder_path = os.path.join(cwd, "test_recorder.py")
    run_command = [sys.executable, test_recorder_path]
    process = subprocess.Popen(run_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, cwd=cwd)
    stdout, stderr = process.communicate()
    if stderr:
        return f"Test Recorder error: {stderr}"
    else:
        return stdout

@app.route('/run-script', methods=['GET'])
def run_script():
    output = run_python_script()
    return jsonify({'output': output})

@app.route('/submit-to-aider', methods=['POST'])
def submit_to_aider():
    data = request.json
    output = data.get('output')
    test_steps = data.get('testSteps')
    
    prompt = f"As a senior automation qa you have to automate the test scenario using the currently implemented methods and only implement what is missing. Please follow the practices used inside the codebase. Below I have provided test steps to automate and also the  Here are the test steps: {test_steps}\n\nHere is the output from the Test Recorder:\n{output}"
    
    # Here you would typically send this prompt to Aider
    # For now, we'll just return the prompt
    return jsonify({
        'status': 'success',
        'message': 'Submitted to Aider',
        'prompt': prompt
    })

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
