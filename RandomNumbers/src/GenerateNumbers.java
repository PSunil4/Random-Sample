import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class GenerateNumbers {
	static int randomNumber = 0;

	private void sendGet() throws IOException {
		String inputLine = "";
		
		String url="https://www.random.org/integers/?num=1&min=100&max=500&col=1&base=10&format=plain&rnd=new";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// Default method is GET
		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET': " + url);
		// 200 OK 
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		while ((inputLine = in.readLine()) != null) {
			randomNumber = Integer.parseInt(inputLine);
		}
		
		in.close();
	}
	// find a prime number
	private int getPrime(int start) {
		for (int i = start; i <= 9999; i++) {
			if (isPrime(i))
				return i;
		}
		return -1;
	}

	private boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	// compute the GCD 
	private int getGCD(int a, int b) {
		if (b == 0)
			return a;
		int remainder = a % b;
		return getGCD(b, remainder);
	}

	private int getModInverse(int a, int b) {
		a = a % b;
		for (int x = 1; x < b; x++) {
			if ((a * x) % b == 1)
				return x;
		}
		return -1;
	}
	
	private void rsa () {
		int p = getPrime(50);
		int q = getPrime(p + 1);
		int n = p * q;
		int p1 = p - 1;
		int q1 = q - 1;
		int p1Multiplyq1 = p1 * q1;

		while (true) {
			int gcd = getGCD(p1Multiplyq1, randomNumber);
			if (gcd == 1)
				break;
			
			randomNumber++;
		}

		// int privateKey = r.getModInverse(randomNumber[0], p1Multiplyq1);

		BigInteger pubKey = BigInteger.valueOf(randomNumber);
		BigInteger p1q1 = BigInteger.valueOf(p1Multiplyq1);

		BigInteger privateKey = pubKey.modInverse(p1q1);

		System.out.println("Public Key: " + randomNumber);
		System.out.println("Private Key: " + privateKey);
	}
	
	public static void main(String[] args) throws Exception {
		GenerateNumbers r = new GenerateNumbers();
		r.sendGet();
		r.rsa();
	}
}
