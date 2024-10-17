from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/ask', methods=['POST'])
def ask():
    question = request.json.get('question')
    try:
        # Run Aider command in the terminal
        result = subprocess.run(['aider', question], capture_output=True, text=True, check=True)
        response = result.stdout
    except subprocess.CalledProcessError as e:
        response = f"An error occurred: {e}"
    except FileNotFoundError:
        response = "Aider command not found. Please ensure Aider is installed and in your PATH."
    return jsonify({'response': response})

if __name__ == '__main__':
    app.run(debug=True)
from flask import Flask, render_template, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        query = request.form['query']
        try:
            result = subprocess.run(['aider', query], capture_output=True, text=True, check=True)
            return jsonify({'result': result.stdout})
        except subprocess.CalledProcessError as e:
            return jsonify({'error': str(e), 'output': e.output}), 400
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
