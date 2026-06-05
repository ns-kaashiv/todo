pipeline {
    agent {
        label 'ec2-fleet'
    }

    tools {
        git 'Default'
        maven 'Maven-3.9.16'
    }

    stages {
        stage('Check out') {
            steps {
                deleteDir()
                git branch: 'main', credentialsId: 'github-pat', url: 'https://github.com/ns-kaashiv/todo.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Docker Build & Push to ECR') {
            steps {
                sh 'aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin 014498666948.dkr.ecr.ap-south-1.amazonaws.com'
                sh 'docker build -t todo .'
                sh 'docker tag todo:latest 014498666948.dkr.ecr.ap-south-1.amazonaws.com/todo:latest'
                sh 'docker push 014498666948.dkr.ecr.ap-south-1.amazonaws.com/todo:latest'
            }
        }
        
        stage('ECS Deploy') {
            steps {
                sh '''aws ecs update-service \
                --cluster todo-cluster \
                --service todo-service \
                --force-new-deployment \
                --region ap-south-1'''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs.'
        }
    }
}
