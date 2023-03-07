#include <unistd.h>
#include <iostream>
#include <cstdlib>
#include <signal.h>

using namespace std;

//Interception of Ctrl+C taken from: https://www.tutorialspoint.com/how-do-i-catch-a-ctrlplusc-event-in-cplusplus


//functions:
void signal_callback_handler(int);


int main (){

    // Register signal and signal handler
    signal(SIGINT, signal_callback_handler);

    while(true){
      cout << "Program processing..." << endl;
      sleep(1);
    }



    return 0;
}


//
void signal_callback_handler(int signum) {
   cout << "Caught signal " << signum << endl;
   // Terminate program
   exit(signum);
}
