using System;
using System.IO;
using System.Text;
using System.Security.Cryptography;

namespace cifradoEncriptAES
{
    public class CifradoEncriptAES
    {

         public  string Encrypt(string key, string iv, string value)
        {
            try{
                AesCryptoServiceProvider aesCrypto = new AesCryptoServiceProvider();
                            aesCrypto.Mode = CipherMode.CBC;
                            aesCrypto.Padding = PaddingMode.PKCS7;
                
                            byte[] keyInBytes = StringToByteArray(key);
                            byte[] ivInBytes = StringToByteArray(iv);
                            aesCrypto.Key = keyInBytes;
                            aesCrypto.IV = ivInBytes;
                
                            var cTransform = aesCrypto.CreateEncryptor();
                            byte[] toEncryptArray = Encoding.ASCII.GetBytes(value);
                            byte[] resultArray = cTransform.TransformFinalBlock(toEncryptArray, 0, toEncryptArray.Length);
                            aesCrypto.Clear();
                            return Convert.ToBase64String(resultArray, 0, resultArray.Length);
                
                } catch(Exception e){
                return  e.InnerException.ToString();
                }
           
        }
                              
        private  byte[] StringToByteArray(String hex)   {

                    try{
                    int NumberChars = hex.Length;
                    byte[] bytes = new byte[NumberChars / 2];
                    for (int i = 0; i < NumberChars; i += 2)
                        bytes[i / 2] = Convert.ToByte(hex.Substring(i, 2), 16);
                    return bytes;
                    }catch(Exception e){
                       Console.WriteLine("Exception => " + e); 
                       return null;
                }
                   
                }
    }
}
