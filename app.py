# app.py
from flask import Flask, request, jsonify, render_template
import subprocess
import os

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
    
    prompt = f"{{OUTPUT}} As a senior automation qa you have to automate the test scenario using the currently implemented methods and only implement what is missing. Please follow the practices used inside the codebase. Here are the test steps: {{TEST_STEPS}}"
    
    # Here you would typically send this prompt to Aider
    # For now, we'll just print it to the console
    print(f"Prompt to be sent to Aider:\n{prompt}\n\nWith OUTPUT:\n{output}\n\nAnd TEST_STEPS:\n{test_steps}")
    
    # In a real scenario, you'd process the Aider response here
    return jsonify({'status': 'success', 'message': 'Submitted to Aider'})

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
