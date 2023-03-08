#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <signal.h>
//#include <unistd.h> //UNIX Only
//#include<sys/wait.h> //UNIX Only
#include <windows.h>
#include <stdio.h>
#include<cstring>


using namespace std;

//Interception of Ctrl+C taken from: https://www.tutorialspoint.com/how-do-i-catch-a-ctrlplusc-event-in-cplusplus


//functions
void signal_callback_handler(int);

//Global Vars
STARTUPINFO si;
PROCESS_INFORMATION pi;

//int pid;

//https://learn.microsoft.com/en-us/windows/win32/procthread/creating-processes
int main(){

    // Register signal and signal handler
    signal(SIGINT, signal_callback_handler);

    //Loop forever to ensure child is always active
    while(true){
        cout << "Starting Proxy" << endl;
        ZeroMemory( &si, sizeof(si) );
        si.cb = sizeof(si);
        ZeroMemory( &pi, sizeof(pi) );

        string s = "java -jar target\\Proxy-0.0.1-SNAPSHOT.jar";
        TCHAR cmd[100] = {0};
        strncpy(cmd,s.c_str(),s.length());

        // Start the child process.
        if( !CreateProcess( NULL,   // No module name (use command line)
            cmd,        // Command line
            NULL,           // Process handle not inheritable
            NULL,           // Thread handle not inheritable
            FALSE,          // Set handle inheritance to FALSE
            0,              // No creation flags
            NULL,           // Use parent's environment block
            NULL,           // Use parent's starting directory
            &si,            // Pointer to STARTUPINFO structure
            &pi )           // Pointer to PROCESS_INFORMATION structure
        )
        {
            printf( "CreateProcess failed (%d).\n", GetLastError() );
            return 1;
        }

        // Wait until child process exits.
        WaitForSingleObject( pi.hProcess, INFINITE );

        // Close process and thread handles.
        CloseHandle( pi.hProcess );
        CloseHandle( pi.hThread );

        sleep(3); //Prevents fork bombing

    }

    return 0;
}


//int main (){
//
//    // Register signal and signal handler
//    signal(SIGINT, signal_callback_handler);
//
//    char *arr[] = {"-jar", "target/Proxy-0.0.1-SNAPSHOT.jar", NULL};
//
//   while(true){
//        cout << "Starting Proxy" << endl;
//        sleep(3); //Helps prevent fork-bombing
//
//        int p = fork();
//
//        //Child
//        if(p == 0){
//            execv("/bin/java", arr);
//            return 0;
//        }
//
//        //Parent
//        else{
//            pid = p; //For killing later
//            wait(NULL);
//       }
//    }
//
//    return 0;
//}


void signal_callback_handler(int signum) {
   cout << "Interrupt Detected..." << endl;
   cout << "Caught signal " << signum << endl;
   cout << "Killing Orphan..." << endl;


   //Kills Orphan
   TerminateThread(pi.hThread,0);
   TerminateProcess(pi.hProcess,0);

   // Close process and thread handles.
    CloseHandle( pi.hProcess );
    CloseHandle( pi.hThread );

    cout << "Orphan Killed!\nGoodbye" << endl;

   // Terminate program
   exit(signum);
}


//void signal_callback_handler(int signum) {
//   cout << "Interrupt Detected..." << endl;
//   cout << "Caught signal " << signum << endl;
//   cout << "Killing Orphan..." << endl;
//
//
//   //Kills Orphan
//   kill(pid, SIGTERM);
//
//   cout << "Orphan Killed!\nGoodbye" << endl;
//
//   // Terminate program
//   exit(signum);
//}



