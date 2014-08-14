/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brownie
 */
public class Telefono {
    private String mac;
    private String noserie;
    private String modelo;
    private String hay;
    private int idemp;
    
    
    public Telefono(String hay, String noserie, String mac, String modelo, int idemp ){
        this.hay = hay;
        this.noserie = noserie;
        this.mac = mac;
        this.modelo = modelo;
        this.idemp = idemp;
    }
    public Telefono(){
        
    }

    /**
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return the noserie
     */
    public String getNoserie() {
        return noserie;
    }

    /**
     * @param noserie the noserie to set
     */
    public void setNoserie(String noserie) {
        this.noserie = noserie;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the idemp
     */
    public int getIdemp() {
        return idemp;
    }

    /**
     * @param idemp the idemp to set
     */
    public void setIdemp(int idemp) {
        
        this.idemp = idemp;
    }

    /**
     * @return the hay
     */
    public String getHay() {
        return hay;
    }

    /**
     * @param hay the hay to set
     */
    public void setHay(String hay) {
        this.hay = hay;
    }
    
}
