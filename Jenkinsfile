pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
               script {
                    bat './gradlew build' //run a gradle task
               }
            }
        }
        stage('Build and sample Run') {
            steps {
               script {
                    bat './gradlew bootRun' //run a gradle task
               }
            }
        }
    }
}
