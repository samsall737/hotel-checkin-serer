package com.nec.hotels.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

 

/**
 *
 * @author HP
 */
interface Qr
{
    public String qr(String Sample);
    public int[]cov (String Sample);
}

class test implements Qr
{
    //@Override
    public String qr(String Sample)
    {
        String qr1 = Sample.replace(" ", "");
        String qr2 = qr1.replaceAll("\\[", "").replaceAll("\\]","");
        return qr2;
    }
    public int[]cov (String Sample)
    {
        String[] qrdec = Sample.split(",");
        int[] ascii = new int[qrdec.length];
        for(int i = 0;i < qrdec.length;i++)
    {
        ascii[i] = Integer.parseInt(qrdec[i]);
    }
        return ascii;
    }
}

@Service
public class QrDecryptionService {
    public int[] convertIntegers(List<Integer> integers)
{
    int[] ret = new int[integers.size()];
    for (int i=0; i < ret.length; i++)
    {
        ret[i] = integers.get(i);
    }
    return ret;
}
      int reverse(int num)
    {
    int rem,res=0;
    while(num!=0)
    {
    rem=num%10;
    res=(res*10)+rem;
    num=num/10;
    }
    return res;
    }

 

    /**
     * @param args the command line arguments
     * @return 
     */
    public  String QrDecrypt(String qrCode) {
        String string=qrCode;
        test myTest=new test();
        String temp1=myTest.qr(string);
        int[]cov1=myTest.cov(temp1);
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList());
        Arrays.stream(cov1).filter(n->n!=23).forEach(numbers::add);
        int[] reverse=convertIntegers(numbers);
        int[] real=new int[reverse.length];
        for (int i=0;i<real.length;i++)
    {
        real[i]=reverse(reverse[i]);
        if(real[i]<13)
        {
            if(real[i]==1)
            {
                real[i]=real[i]*100;
            }
            else
            {
                real[i]=real[i]*10;
            }
        }
    }
    char[]ch=new char[numbers.size()];
    for(int i=0;i<numbers.size();i++)
    {
        ch[i]=(char)real[i];
    }
    String result =Arrays.toString(ch);
    String result1=result.trim();
    String result2 = result1.replace(" ", "");
    String result3 = result2.replace(",", "");
    String result4 = result3.replaceAll("\\[", "").replaceAll("\\]","");
    String salt = "srd99";
    String dummy = "";
    String bookingid = result4.replace(salt, dummy);
    return  bookingid.trim();
    }
    
}