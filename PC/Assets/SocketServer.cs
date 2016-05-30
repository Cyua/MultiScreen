using UnityEngine;
using System;
using System.Collections;
using System.Net;
using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Threading;

public class SocketServer : MonoBehaviour {
	private static int myProt = 10000;	//port
	private static byte[] result = new byte[1024];
	static Socket serverSocket;
	// Use this for initialization
	void Start () {
		OpenServer ();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	void OpenServer(){
		IPAddress ipAdr = IPAddress.Parse ("192.168.1.105");
		serverSocket = new Socket (AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
		serverSocket.Bind (new IPEndPoint(ipAdr,myProt));
		serverSocket.Listen (20);
		Debug.Log ("Start Listening ================");
		Thread myThread = new Thread (ListenClientConnect);
		myThread.Start ();
	}

	private static void ListenClientConnect(){
		while (true) {
			Socket clientSocket = serverSocket.Accept();
			clientSocket.Send(Encoding.ASCII.GetBytes("accept"));
			Thread receiveThread = new Thread(ReceiveMessage);
			receiveThread.Start(clientSocket);
		}
	}

	private static void ReceiveMessage(object clientSocket){
		Socket myClientSocket = (Socket)clientSocket;
		while (true) {
			try{
				int receiveNumber = myClientSocket.Receive(result);
				Console.WriteLine("accept from message: {0}",Encoding.ASCII.GetString(result,0,receiveNumber));
			} 
			catch(Exception ex){
				Console.WriteLine(ex.Message);
				myClientSocket.Shutdown(SocketShutdown.Both);
				myClientSocket.Close();
				break;
			}
		}
	}
}


