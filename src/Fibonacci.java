import java.util.*;
public class Fibonacci {
	int [] lookup;
	
	Fibonacci()
	{
		lookup = new int [5000];
	}
	
	//Memoization
	int nthFib(int n)
	{
		if(lookup[n] == 0)
		{
			if(n<=1)
				lookup[n] = n;
			else
				lookup[n] = nthFib(n-1)+nthFib(n-2);
		}
		return lookup[n];
	}
	
	//Tabulation
	int fab(int n)
	{
		int [] f = new int[n+1];
		f[0] = 0;
		f[1] = 1;
		for(int i = 2; i<=n;i++)
			f[i] = f[i-1]+f[i-2];
		
		return f[n];
	}
	
	//Longest Increasing Subsequence DP solution
	public int longestIncSubseq(int []arr)
	{
		//Memoize the length of each sub problem
		int [] l = new int[arr.length];
	    for(int i = 0; i< arr.length; i++)
	        l[i] = 1;
	    
	    for(int i = 1; i< l.length; i++)
	    {
	        for(int j = 0; j<i; j++)
	            if(arr[i]>arr[j] && l[i] < l[j]+1)
	                l[i] = l[j]+1;
	    }
	    
	    int max = 0;
	    for(int i = 0; i<l.length; i++)
	    {
	        if(max <l[i])
	            max = l[i];
	    }
	    return max;
	}
	
	// This is naive Approach
	///Longest common sub sequence
	// LCS(X[0..m-1],Y[0..n-1]) = 1+ LCS(X[0..m-2],Y[0..n-2]) if the last character in both array matches
	// LCS(X[0..m-1],Y[0..n-1]) = Max(LCS(X[0..m-2],Y[0..n-1]),LCS(X[0..m-1],Y[0..n-2])) if the last character do not match
	public int LCS(char[]X,char[]Y,int m, int n)
	{
		//base condition
		if(m==0 || n==0)
			return 0;
		if(X[m-1] == Y[n-1])
			return 1+LCS(X,Y,m-1,n-1);
		else
			return Math.max(LCS(X,Y,m-1,n), LCS(X,Y,m,n-1));
	}
	
	//Dynamic programming using memoization
	public String LCSDP(char[]X,char [] Y, int m, int n)
	{
		int [][] lookup = new int [m][n];
		
		for(int i = 0; i<m;i++)
		{
			for(int j = 0; j<n; j++)
			{
				if(i ==0 || j==0)
					lookup[i][j]=1;
				else if(X[i] == Y[j])
					lookup[i][j] = 1+ lookup[i-1][j-1];
				else
					lookup[i][j] = Math.max(lookup[i-1][j], lookup[i][j-1]);
			}
		}
		
		//return lookup[m-1][n-1];
		char[] arr = new char[lookup[m-1][n-1]];
		
		int i = m-1, j = n-1;
		int index = arr.length;
		
		while(i>0 && j>0)
		{
			if(X[i]==Y[j])
			{
				arr[index-1] = X[i];
				i--;j--; index--;
			}
			else if(lookup[i-1][j]>lookup[i][j-1])
				i--;
			else
				j--;
				
		}
		if(X[0]==Y[0])
			arr[0] = X[0];
		return Arrays.toString(arr);
	}
	///edit distance => change str1 to str2
	///3 operations possible Insert, Remove, Replace
	///start from the last element 
	///if last character match continue to the remaining string
	/// else return 1+ minimum of Insert, Remove and Replace operation
	// Naive Solution
	public int editDistance(char[] X, char[] Y, int m , int n)
	{
		if(m==0)
			return n;//Insert n elements 
		if(n==0)
			return m;//remove m elements from the first string
		
		if(X[m-1] == Y[n-1])
			return editDistance(X,Y,m-1,n-1);
		else
			return 1+ Math.min(Math.min(editDistance(X,Y,m,n-1), editDistance(X,Y,m-1,n)), editDistance(X,Y,m-1,n-1));
		
	}
	//DP solution
	
	public int editDistanceDP(char[] X, char []Y, int m, int n)
	{
		int [] [] lookup = new int [m][n];
		
		for(int i = 0; i<m; i++)
		{
			for(int j = 0; j<n; j++)
			{
				if(i==0)//if first string is empty
					lookup[i][j] = j;
				else if(j == 0) //second string remove all from first
					lookup[i][j] = i;
				else if(X[i]==Y[j])
					lookup[i][j] = lookup[i-1][j-1];
				else
					lookup[i][j] = 1 + Math.min(Math.min(lookup[i-1][j], lookup[i][j-1]), lookup[i-1][j-1]);
				
			}
		}
		return lookup[m-1][n-1];
	}
	
	static int[] sortIntersect(int[] f, int[] m) {

        HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
        int [] result = new int [f.length < m.length ? m.length:f.length];
        
        for(int i = 0; i <f.length; i++)
        {
            if(hmap.containsKey(f[i]))
            {
                int count = hmap.get(f[i]);
                hmap.remove(f[i]);
                hmap.put(f[i],count+1);
            }
            else
                hmap.put(f[i],1);
        }
        
        int resultIdx = 0;
        for(int j = 0;j <m.length; j++)
        {
            if(hmap.containsKey(m[j]))
            {
                if(hmap.get(m[j]) > 0)
                {
                    result[resultIdx++] = m[j];
                    int count = hmap.get(m[j]);
                    hmap.remove(m[j]);
                    hmap.put(m[j],count-1);
                }
            }    
        }
        Arrays.sort(result);
        for(int i = 0; i<result.length /2; i++)
        {
            int temp = result[i];
            result[i] = result[result.length -i -1];
            result[result.length -i -1] = temp;
        }
        
        return result;
    }
	public static void main(String args[])
	{
		Fibonacci fb = new Fibonacci();
		System.out.println("10th fibonacci number is : " + fb.fab(10));
		int [] f = {7000,7000,12000,13000,6900};
		int []m = {6900,7000,7000,12000,};
		int [] result = sortIntersect(f,m);
		for(int i = 0; i<result.length; i++)
		{
			System.out.println(result[i]);
		}
		
	}
}
