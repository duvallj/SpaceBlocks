package rsaUtils;

class PrimeGen {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public int[] getPrimes(){
		int[] primes = new int[0];
		for(int x = 2; x < 1001; x++){
			int f = factor(x)[0];
			if(f==x){
				primes = add_num(primes, x);
			}
		}
		return(primes);
	}
	/**
	 * @param num
	 * @return
	 */
	public static int[] factor(int num){
		int[] factor_list = new int[0];
		int temp = num;
		int divisor = 2;
		while (divisor*divisor<=temp){
			while (temp % divisor == 0){
				factor_list = add_num(factor_list, divisor);
				temp /= divisor;
			}
			divisor++;
		}
		if(temp > 1){factor_list=add_num(factor_list, temp);}
		return factor_list;
	}
	public static int[] add_num(int num_list [] , int num){
		int temp [] = new int[num_list.length+1];
		for(int counter = 0; counter < num_list.length; counter++){
			temp[counter] = num_list[counter];
		}
		temp[num_list.length] = num;
		return temp;
	}
	
	public int[] add_numP(int numList [], int num){
		return add_num(numList, num);
	}
	
	public int[] factorP(int numP){
		return factor(numP);
	}

}

public class Num_gen{
	public static void main(String[] args){
		//Nothing, should be imported
	}
	
	public long[] gen(){

		PrimeGen all = new PrimeGen();
		int[] allPrimes = all.getPrimes();
		
		int[] smallPrimes = new int[9];
		for(byte x=1; x<10; x++){
			smallPrimes[x-1] = allPrimes[x];
		}
		
		int[] bigPrimes = new int[137];
		for(int x=31; x<allPrimes.length; x++){
			bigPrimes[x-31] = allPrimes[x];
		}
		
		int M = -1;
		long D = 0;
		int P = 0;
		
		while(M<D||0>D){
			
			int a = bigPrimes[(int)(Math.random()*137)];
			int b = bigPrimes[(int)(Math.random()*137)];
			
			M = a*b;
			
			int n1 = (a-1)*(b-1);
			int[] n1Factors = all.factorP(n1);
			
			int[] pFactors = new int[0];
			
			for(byte i=0; i<n1Factors.length; i++){
				
				boolean cont = false;
				
				for(byte j=0; j<9; j++){
					if(n1Factors[i] == smallPrimes[j]){
						cont = true;
					}
				}
				
				if(!(cont)){
					pFactors = all.add_numP(pFactors, n1Factors[i]);
				}
			}
			
			P = smallPrimes[(int)(Math.random()*9)]*smallPrimes[(int)(Math.random()*9)];
			
			int x = 0;
			while((n1 * x + 1) % P != 0){
				x++;
			}
			
			D = (int)(n1 * x + 1) / P;
		}
		
		long[] toReturn = {M, P, D};
		return toReturn;
	}
	

	public long powmod(long a, long b, long c ){
		
		long result = 1;
		long base = a % c;
		long exp = b;
		while(exp>0){
			if(exp%2==1){
				result = (result * base) % c;
			}
			 exp = exp >> 1;
			base = (base*base) % c;
		}
		
		return result;
		
	}
}