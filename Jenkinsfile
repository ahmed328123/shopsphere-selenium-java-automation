pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
        stage('Repository auschecken') {
            steps {
                checkout scm
            }
        }

        stage('Tests ausführen') {
            steps {
                bat '''
                    mvn -B clean test -Pfull ^
                    -Dheadless=true ^
                    -Dbrowser=chrome ^
                    -Dmaven.test.failure.ignore=true
                '''
            }
        }
    }

    post {
        always {
            junit(
                allowEmptyResults: true,
                testResults: 'target/surefire-reports/*.xml'
            )

            archiveArtifacts(
                allowEmptyArchive: true,
                artifacts: 'screenshots/**/*, reports/**/*, target/surefire-reports/**/*'
            )
        }
    }
}