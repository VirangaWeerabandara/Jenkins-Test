pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/VirangaWeerabandara/Jenkins-Test.git'
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
