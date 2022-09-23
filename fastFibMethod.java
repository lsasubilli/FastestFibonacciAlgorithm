public BigInteger fib(BigInteger n) {
    if (n.equals(BigInteger.ZERO))
        return BigInteger.ZERO;
    if (n.equals(BigInteger.ONE) || n.equals(BigInteger.valueOf(2)))
        return BigInteger.ONE;
    
    BigInteger index = n;
    
    //we could have 2 Lists instead of a map
    Map<BigInteger,BigInteger> termsToCalculate = new TreeMap<BigInteger,BigInteger>();
    
    //add every index needed to calculate  index n
    populateMapWhitTerms(termsToCalculate, index);
    
    termsToCalculate.put(n,null); //finally add n to map
    
    Iterator<Map.Entry<BigInteger, BigInteger>> it = termsToCalculate.entrySet().iterator();//it 
    it.next(); //it = key number 1, contains fib(1);
    it.next(); //it = key number 2, contains fib(2);
    
    //map is ordered
    while (it.hasNext()) {
        Map.Entry<BigInteger, BigInteger> pair = (Entry<BigInteger, BigInteger>)it.next();//first it = key number 3
        index = (BigInteger) pair.getKey();
        
        if(index.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            //index is divisible by 2
            //F(2k) = F(k)[2F(k+1)−F(k)]
            pair.setValue(termsToCalculate.get(index.divide(BigInteger.valueOf(2))).multiply(
                    (((BigInteger.valueOf(2)).multiply(
                            termsToCalculate.get(index.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)))).subtract(
                                    termsToCalculate.get(index.divide(BigInteger.valueOf(2)))))));
        }
        else {
            //index is odd
            //F(2k+1) = F(k+1)^2+F(k)^2
            pair.setValue((termsToCalculate.get(index.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)).multiply(
                    termsToCalculate.get(index.divide(BigInteger.valueOf(2)).add(BigInteger.ONE)))).add(
                            (termsToCalculate.get(index.divide(BigInteger.valueOf(2))).multiply(
                            termsToCalculate.get(index.divide(BigInteger.valueOf(2))))))
                    );
        }
    }
    
    // fib(n) was calculated in the while loop
    return termsToCalculate.get(n);
}

private void populateMapWhitTerms(Map<BigInteger, BigInteger> termsToCalculate, BigInteger index) {
    if (index.equals(BigInteger.ONE)) { //stop
        termsToCalculate.put(BigInteger.ONE, BigInteger.ONE);
        return;
        
    } else if(index.equals(BigInteger.valueOf(2))){
        termsToCalculate.put(BigInteger.valueOf(2), BigInteger.ONE);
        return;
        
    } else if(index.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
        // index is divisible by 2
        // FORMUMA: F(2k) = F(k)[2F(k+1)−F(k)]
                    
        // add F(k) key to termsToCalculate (the key is replaced if it is already there, we are working with a map here)
        termsToCalculate.put(index.divide(BigInteger.valueOf(2)), null);
        populateMapWhitTerms(termsToCalculate, index.divide(BigInteger.valueOf(2)));

        // add F(k+1) to termsToCalculate
        termsToCalculate.put(index.divide(BigInteger.valueOf(2)).add(BigInteger.ONE), null);
        populateMapWhitTerms(termsToCalculate, index.divide(BigInteger.valueOf(2)).add(BigInteger.ONE));
        
    } else {
        // index is odd
        // FORMULA: F(2k+1) = F(k+1)^2+F(k)^2

        // add F(k+1) to termsToCalculate
        termsToCalculate.put(((index.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)).add(BigInteger.ONE)),null);
        populateMapWhitTerms(termsToCalculate,((index.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)).add(BigInteger.ONE)));

        // add F(k) to termsToCalculate
        termsToCalculate.put((index.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)), null);
        populateMapWhitTerms(termsToCalculate, (index.subtract(BigInteger.ONE)).divide(BigInteger.valueOf(2)));
    }
   }
