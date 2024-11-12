# app.py
from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)


def run_python_script():
    import subprocess
    import os
    import sys

    # Get the current working directory
    cwd = os.getcwd()

    # Construct the full path to test_recorder.py
    test_recorder_path = os.path.join(cwd, "test_recorder.py")

    # Run the TestRecorder using Python
    run_command = [sys.executable, test_recorder_path]
    process = subprocess.Popen(run_command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, cwd=cwd)

    # Capture and return the output
    stdout, stderr = process.communicate()
    if stderr:
        return f"Test Recorder error: {stderr}"
    else:
        return f"Test Recorder output:\n{stdout}"


@app.route('/run-script', methods=['GET'])
def run_script():
    output = run_python_script()
    return jsonify({'output': output})

@app.route('/get-results', methods=['GET'])
def get_results():
    # This function should be implemented to return the results of the test recorder
    # For now, we'll return a placeholder
    return jsonify({
        'page_clicks_map': {},
        'page_hovers_map': {}
    })


@app.route('/submit', methods=['POST'])
def submit():
    data = request.json
    script_output = data.get('script_output')
    user_input = data.get('user_input')

    # Combine the script output and user input
    command = f"echo {script_output} {user_input}"

    # Execute the command using subprocess
    try:
        result = subprocess.run(command, shell=True, check=True, capture_output=True, text=True)
        return jsonify({'result': result.stdout})
    except subprocess.CalledProcessError as e:
        return jsonify({'error': e.stderr}), 400


if __name__ == '__main__':
    app.run(debug=True)
