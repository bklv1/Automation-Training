# app.py
from flask import Flask, request, jsonify, render_template
import os
import sys

from aider.coders import Coder
from aider.io import InputOutput
from aider.main import main as cli_main

app = Flask(__name__)

class CaptureIO(InputOutput):
    def __init__(self):
        super().__init__(pretty=False, yes=True, dry_run=True)
        self.captured_output = []

    def tool_output(self, msg, log_only=False):
        if not log_only:
            self.captured_output.append(msg)
        super().tool_output(msg, log_only=log_only)

    def tool_error(self, msg):
        self.captured_output.append(msg)
        super().tool_error(msg)

    def tool_warning(self, msg):
        self.captured_output.append(msg)
        super().tool_warning(msg)

    def get_captured_output(self):
        output = "\n".join(self.captured_output)
        self.captured_output = []
        return output

def get_coder():
    coder = cli_main(return_coder=True)
    if not isinstance(coder, Coder):
        raise ValueError(coder)
    if not coder.repo:
        raise ValueError("GUI can currently only be used inside a git repo")

    io = CaptureIO()
    coder.commands.io = io
    coder.auto_commit = False
    coder.yield_stream = True
    coder.stream = True
    coder.pretty = False

    return coder

coder = get_coder()

@app.route('/run-script', methods=['GET'])
def run_script():
    cwd = os.getcwd()
    test_recorder_path = os.path.join(cwd, "test_recorder.py")
    
    try:
        with open(test_recorder_path, 'r') as file:
            script_content = file.read()
        
        # Create a local namespace to execute the script content
        local_namespace = {}
        exec(script_content, local_namespace)
        
        # Access the TestRecorder class from the local namespace
        TestRecorder = local_namespace.get('TestRecorder')
        if TestRecorder is None:
            raise ValueError("TestRecorder class not found in the script")
        
        recorder = TestRecorder()
        recorder.run()
        
        output = coder.commands.io.get_captured_output()
        return jsonify({'output': output})
    except Exception as e:
        return jsonify({'error': str(e)})

@app.route('/submit-to-aider', methods=['POST'])
def submit_to_aider():
    data = request.json
    output = data.get('output')
    test_steps = data.get('testSteps')

    prompt = (f"As a senior automation qa you have to automate the test scenario using the currently"
              f" implemented methods and only implement what is missing. Please follow the practices used inside the codebase. "
              f"Here are the test steps: {test_steps}\n\nHere is the output from the Test Recorder:\n{output}")

    try:
        reply = coder.send_message(prompt)
        return jsonify({
            'status': 'success',
            'message': 'Aider response received',
            'output': reply
        })
    except Exception as e:
        return jsonify({
            'status': 'error',
            'message': 'An error occurred',
            'output': str(e)
        })

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
