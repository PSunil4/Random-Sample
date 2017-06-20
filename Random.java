import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class Random {
	static int randomNumber;

	private void sendGet() throws Exception {
		String inputLine;
		BufferedReader in = null;
		
		try {
			String url = "https://www.random.org/integers/?num=2&min=1000&max=5000&col=1&base=10&format=plain&rnd=new";
	
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			int responseCode = con.getResponseCode();
			System.out.println("Sending 'GET': " + url);
			System.out.println("Response Code : " + responseCode);
	
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	
			while ((inputLine = in.readLine()) != null) {
				randomNumber = Integer.parseInt(inputLine);
			}
		}catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			in.close();
		}
	}

	public int getPrime(int start) {
		for (int i = start; i <= 9999; i++) {
			if (isPrime(i))
				return i;
		}
		return -1;
	}

	public boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public int getGCD(int a, int b) {
		if (b == 0)
			return a;
		int remainder = a % b;
		return getGCD(b, remainder);
	}

	public int getModInverse(int a, int b) {
		a = a % b;
		for (int x = 1; x < b; x++) {
			if ((a * x) % b == 1)
				return x;
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		Random r = new Random();
		r.sendGet();
		int p = r.getPrime(1000);
		int q = r.getPrime(p + 1);

		int n = p * q;

		int p1 = p - 1;
		int q1 = q - 1;
		int p1Multiplyq1 = p1 * q1;

		while (true) {
			int gcd = r.getGCD(p1Multiplyq1, randomNumber);

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

}
