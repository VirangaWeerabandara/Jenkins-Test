pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/VirangaWeerabandara/Jenkins-Test.git'
            }
        }
        
        stage('Check Python Installation') {
            steps {
                script {
                    def pythonInstalled = sh(script: 'which python3', returnStatus: true)
                    
                    // Check if Python is installed
                    if (pythonInstalled != 0) {
                        error 'Python is not installed on this machine.'
                    } else {
                        echo 'Python is installed.'
                    }
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    sh """
                    python3 -m venv venv
                    source venv/bin/activate
                    pip install -r requirements.txt || echo "No requirements.txt found"
                    """
                }
            }
        }
        
        stage('Run Python Script') {
            steps {
                script {
                    sh """
                    source venv/bin/activate
                    python jenkins_test.py
                    """
                }
            }
        }
    }
}
