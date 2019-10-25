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
                            bat 'D:\LOCAL_REPO\PsExec.exe \\itapair06-w10 C:\Users\alvariy\Desktop\PROD_REPO\executeBat.bat' //run a gradle task
                       }
                    }
                }
    }
}
