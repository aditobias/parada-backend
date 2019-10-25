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
    }
}
