# app.py
from flask import Flask, request, jsonify, render_template
import subprocess
import os
import sys
import tempfile

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
    
    prompt = f"As a senior automation qa you have to automate the test scenario using the currently implemented methods and only implement what is missing. Please follow the practices used inside the codebase. Here are the test steps: {test_steps}\n\nHere is the output from the Test Recorder:\n{output}"
    
    # Create a temporary file to store the prompt
    with tempfile.NamedTemporaryFile(mode='w+', delete=False, suffix='.txt') as temp_file:
        temp_file.write(prompt)
        temp_file_path = temp_file.name

    try:
        # Run Aider command
        aider_command = f"aider {temp_file_path}"
        process = subprocess.Popen(aider_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True, text=True)
        stdout, stderr = process.communicate()

        if stderr:
            return jsonify({
                'status': 'error',
                'message': 'Error from Aider',
                'output': stderr
            })
        else:
            return jsonify({
                'status': 'success',
                'message': 'Aider response received',
                'output': stdout
            })
    finally:
        # Clean up the temporary file
        os.unlink(temp_file_path)

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
