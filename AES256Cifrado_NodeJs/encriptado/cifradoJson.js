const config = require('config');
const log4js = require("log4js");
const logger = log4js.getLogger("cifradoJson");
const CryptoJS = require("crypto-js");
const crypto = require('crypto');
const iv = new Buffer.from('0000000000000000');

//JAVA

const java = require("java");
java.asyncOptions = {
  asyncSuffix: undefined,     // Don't generate node-style methods taking callbacks
  syncSuffix: "",              // Sync methods use the base name(!!)
  promiseSuffix: "Promise",   // Generate methods returning promises, using the suffix Promise.
  promisify: require('util').promisify // Needs Node.js version 8 or greater, see comment below
};
java.classpath.push("/Users/desarrolladorios1/Desktop/dev/NodejsSandbox/AES256Cifrado/jar/EncripcionDesencripcionTS.jar");

module.exports = {

  Encrypt_File: function(req) {
    logger.info("Encrypt_File  req => ", req);
     var ciphertext = CryptoJS.AES.encrypt(JSON.stringify(req), config.get('ENCRIPT'));
     return ciphertext.toString();
   },

  //funcion para descifrar el archivo
  Decrypt_File: function(ciphertext) {
    let bytes = CryptoJS.AES.decrypt(ciphertext.toString(), config.get('ENCRIPT'));
    return bytes.toString(CryptoJS.enc.Utf8);
  },

  EncryptHex(plainText){
    var b64 = CryptoJS.AES.encrypt(plainText, config.get('ENCRIPT')).toString();
    var e64 = CryptoJS.enc.Base64.parse(b64);
    var eHex = e64.toString(CryptoJS.enc.Hex);
    return eHex;
    },

    DecryptHex(cipherText){
    var reb64 = CryptoJS.enc.Hex.parse(cipherText);
    var bytes = reb64.toString(CryptoJS.enc.Base64);
    var decrypt = CryptoJS.AES.decrypt(bytes, config.get('ENCRIPT'));
    var plain = decrypt.toString(CryptoJS.enc.Utf8);
    return plain;
    },


     // Method to encript, consuming services from Java
      ENCRIPT_CRYPTO(plainText){
        var decodeKey = crypto.createHash('sha256').update(config.get('ENCRIPT'), 'utf-8').digest();
        var cipher = crypto.createCipheriv('aes-256-cbc', decodeKey, iv);
        return cipher.update(plainText, 'utf8', 'hex') + cipher.final('hex');

      },
      // Method to decript, consuming services from Java
      DECRIPT_CRYPTO(encryptedHex){
        var encodeKey = crypto.createHash('sha256').update(config.get('ENCRIPT'), 'utf-8').digest();
        var cipher = crypto.createDecipheriv('aes-256-cbc', encodeKey, iv);
        return cipher.update(encryptedHex, 'hex', 'utf8') + cipher.final('utf8');

      },



      Encrypt_File_RSA(plainText){
        var EncripcionDesencripcionTS = java.import('EncripcionDesencripcionTS.EncripcionDesencripcionTS');
        var RSA = new EncripcionDesencripcionTS();
        var encript = RSA.EncripcionTS(plainText)
        return encript
      },

      Decrypt_File_RSA(encrypted){
        var EncripcionDesencripcionTS = java.import('EncripcionDesencripcionTS.EncripcionDesencripcionTS');
        var RSA = new EncripcionDesencripcionTS();
        var decrypt = RSA.DesencripcionTS(encrypted)
        return decrypt
      }




      


};