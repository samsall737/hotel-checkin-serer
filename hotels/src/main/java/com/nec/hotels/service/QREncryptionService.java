package com.nec.hotels.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nec.hotels.utils.Constants;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QREncryptionService {

	@Value("${qrcode.path}")
	private String qrCodePath;



	private void generateQRCode(String text, int width, int height, String filePath)
            throws Exception {
        QRCodeWriter qcwobj = new QRCodeWriter();
        BitMatrix bmobj = qcwobj.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path pobj = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bmobj, "PNG", pobj);
                             }
     int reverse(int num)
    {
        if(num==100)
        {
            return 001;
        }
    int rem,res=0;
    while(num!=0)
    {
    rem=num%10;
    res=(res*10)+rem;
    num=num/10;
    }
    return res;
    }
    private boolean containsWhiteSpace(final String testCode){
    if(testCode != null){
        for(int i = 0; i < testCode.length(); i++){
            if(Character.isWhitespace(testCode.charAt(i))){
                return true;
            }
        }
    }
    return false;
}
    /**
     * @param args the command line arguments
     */
    public void generate(String bookingId)throws Exception
    {
         //final String qcip = qrCodePath + bookingId + Constants.PNG;
         final String qcip = "hotels\\src\\main\\resources\\qrcode\\"+bookingId+".png" ;
        String userName = bookingId;
        boolean test=containsWhiteSpace(userName);
        if(test==false)
        {
        }
        else
        {
            System.exit(0);
        }
        String Salt = "srd99";
    //Salt Addition
        StringBuilder sb = new StringBuilder(userName);
        sb.append(Salt);
        String result = sb.toString();
        String str = result;
        String str1 = str.replaceAll("\\B|\\b", " ");
        int[]ascii= new int[str1.length()];
        for (int j=0; j < str1.length();j++)
        {
            ascii[j]= str1.charAt(j);
        }
       int[]str2=new int[str1.length()];


        for (int i=0;i<str1.length();i++)
        {
                        str2[i]=reverse(ascii[i]);

        }

         String qr=Arrays.toString(str2);
         generateQRCode(qr,400, 400, qcip);


    }

}
 




