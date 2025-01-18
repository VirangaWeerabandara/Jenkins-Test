pipeline {
    agent any
    
    // If you need specific tools or environment variables
    environment {
        GITHUB_REPO = 'https://github.com/VirangaWeerabandara/Jenkins-Test.git'
        BRANCH_NAME = 'main'  // or your target branch
        PYTHON_SCRIPT = 'jenkins-test.py'  // name of your Python script
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Clean workspace and checkout code from GitHub
                cleanWs()
                git branch: env.BRANCH_NAME,
                    url: env.GITHUB_REPO
            }
        }
        
        stage('Verify Python') {
            steps {
                // Verify Python installation
                sh 'python3 --version'
            }
        }
        
        stage('Run Python Script') {
            steps {
                // Execute the Python script
                sh 'python3 ${PYTHON_SCRIPT}'
            }
            
            // Optional: Handle script output or failure
            post {
                success {
                    echo 'Python script executed successfully'
                }
                failure {
                    echo 'Python script execution failed'
                }
            }
        }
    }
    
    // Optional: Global post-build actions
    post {
        always {
            cleanWs()  // Clean workspace after execution
        }
    }
}