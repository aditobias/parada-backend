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
        stage('Deploy Test') {
            steps {
               script {
                   sh: step 1 put jar file to pc2 share folder
                   step 2 put bat file in pc2
                   step 3 trigger bat in pc2
               }
            }
        }
    }
}
