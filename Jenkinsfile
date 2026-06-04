pipeline {
    agent {
        label 'ec2-fleet'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Build') {
            steps {
                echo 'Webhook Triggered'
            }
        }
    }
}
