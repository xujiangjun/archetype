package com.xujiangjun.archetype.manager.util.algorithm;

import com.xujiangjun.archetype.enums.ResponseEnum;
import com.xujiangjun.archetype.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密算法工具类
 * https://mp.weixin.qq.com/s/Plkor9SRVojimspH1NBzXQ
 *
 * @author xujiangjun
 * @since 2018.08.03
 */
@Slf4j
public class RSAUtils {

    /** 加密算法 */
    private static final String KEY_ALGORITHM = "RSA";

    /** 签名算法 */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** 公钥算法 */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** 私钥算法 */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data 加密数据
     * @param key  私钥
     * @return
     */
    public static String sign(byte[] data, String key){
        try {
            byte[] keyBytes = Base64.decodeBase64(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            log.error("数字签名异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 校验数字签名
     *
     * @param data 加密数据
     * @param key  公钥
     * @param sign 数字签名
     * @return 校验成功返回true 失败返回false
     */
    public static boolean verify(byte[] data, String key, String sign){
        try {
            byte[] keyBytes = Base64.decodeBase64(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            log.error("数字签名校验异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key  私钥
     * @return
     */
    public static byte[] encryptByPrivateKey(String data, String key){
        try {
            // 对公钥解密
            byte[] keyBytes = Base64.decodeBase64(key);
            // 取得公钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            log.error("私钥加密异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return
     */
    public static byte[] encryptByPublicKey(String data, String key){
        try {
            // 对公钥解密
            byte[] keyBytes = Base64.decodeBase64(key);
            // 取得公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            log.error("公钥加密异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  私钥
     * @return
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key){
        try {
            // 对公钥解密
            byte[] keyBytes = Base64.decodeBase64(key);
            // 取得公钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("私钥解密异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  公钥
     * @return
     */
    public static byte[] decryptByPublicKey(byte[] data, String key){
        try {
            // 对公钥解密
            byte[] keyBytes = Base64.decodeBase64(key);
            // 取得公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("公钥解密异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥map
     * @return 公钥
     */
    public static String getPublicKey(Map<String, Key> keyMap){
        Key publicKey = keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥map
     * @return 私钥
     */
    public static String getPrivateKey(Map<String, Key> keyMap){
        Key privateKey = keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(privateKey.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return keyMap
     */
    public static Map<String, Key> initKey(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Map<String, Key> keyMap = new HashMap<>(2);
            keyMap.put(PUBLIC_KEY, keyPair.getPublic());
            keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            log.error("初始化密钥异常", e);
            throw new BusinessException(ResponseEnum.SYSTEM_ERROR);
        }
    }

    public static void main(String[] args) {
        Map<String, Key> keyMap = initKey();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);

        System.out.println(keyMap);
        System.out.println("-----------------------------------");
        System.out.println(publicKey);
        System.out.println("-----------------------------------");
        System.out.println(privateKey);
        System.out.println("-----------------------------------");
        byte[] encryptByPrivateKey = encryptByPrivateKey("Hello", privateKey);
        byte[] encryptByPublicKey = encryptByPublicKey("Hello", publicKey);
        System.out.println("-----------------------------------");
        System.out.println(encryptByPrivateKey);
        System.out.println("-----------------------------------");
        System.out.println(encryptByPublicKey);
        System.out.println("-----------------------------------");
        String signature = sign(encryptByPrivateKey, privateKey);
        System.out.println(signature);
        System.out.println("-----------------------------------");
        boolean verify = verify(encryptByPrivateKey, publicKey, signature);
        System.out.println(verify);
        System.out.println("-----------------------------------");
        byte[] decryptByPublicKey = decryptByPublicKey(encryptByPrivateKey, publicKey);
        byte[] decryptByPrivateKey = decryptByPrivateKey(encryptByPublicKey, privateKey);
        System.out.println(new String(decryptByPublicKey));
        System.out.println("-----------------------------------");
        System.out.println(new String(decryptByPrivateKey));
    }
}
