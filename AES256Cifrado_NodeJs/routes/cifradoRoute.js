const express = require("express");
const router = express.Router();


//working with log files
const log4js = require("log4js");
const logger = log4js.getLogger("cifradoRoute");
const cifradojson = require('../encriptado/cifradoJson');

// Get an item By ID from the database
router.post("/cifrar", async (req, res) => {
    let crifrado = cifradojson.Encrypt_File(req.body);
    res.status(200).send({ Cifrado : crifrado}); 
});


router.post("/descifrar", async (req, res) => {
    let wsDatos =  JSON.parse(cifradojson.Decrypt_File(req.body));
    res.status(200).send(wsDatos); 
});


// Get an item By ID from the database
router.post("/hex/cifrar", async (req, res) => {
    logger.info("cifrar body => ", req.body);
    var cipher = cifradojson.EncryptHex(JSON.stringify(req.body));
    logger.info("cifrado => ", cipher);
    res.status(200).send({ cifrado : cipher})
});


router.post("/hex/descifrar", async (req, res) => {
         let decipher = cifradojson.DecryptHex(req.body);
         logger.info("decipher => " , decipher);
    // let wsDatos =  JSON.parse(cifradojson.Decrypt128(req.body));
    res.status(200).send(JSON.parse(decipher)); 
});


// Get an item By ID from the database
router.post("/cripto/cifrar", async (req, res) => {
    logger.info("cifrar body => ", req.body);
    var cipher = cifradojson.ENCRIPT_CRYPTO(JSON.stringify(req.body));
    logger.info("cifrado => ", cipher);
    res.status(200).send({ cifrado : cipher})
});


router.post("/cripto/descifrar", async (req, res) => {
         let decipher = cifradojson.DECRIPT_CRYPTO(req.body);
         logger.info("decipher => " , decipher);
    // let wsDatos =  JSON.parse(cifradojson.Decrypt128(req.body));
    res.status(200).send(JSON.parse(decipher)); 
});

// Get an item By ID from the database
router.post("/rsa/cifrar", async (req, res) => {
    logger.info("cifrar body => ", req.body);
    var cipher = cifradojson.Encrypt_File_RSA(JSON.stringify(req.body));
    logger.info("cifrado => ", cipher);
    res.status(200).send({ cifrado : cipher})
});


router.post("/rsa/descifrar", async (req, res) => {

         let decipher = cifradojson.Decrypt_File_RSA(req.body);
         logger.info("decipher => " , decipher);
    // let wsDatos =  JSON.parse(cifradojson.Decrypt128(req.body));
    res.status(200).send(decipher); 
});


module.exports = router;
