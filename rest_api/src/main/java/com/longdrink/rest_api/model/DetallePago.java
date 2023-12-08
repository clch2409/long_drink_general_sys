package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "DetallePago")
@Table(name = "detalle_pago")
public class DetallePago implements Serializable {
    @Id
    @Column(name ="cod_detalle_pago")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codDetallePago")
    private Long codDetallePago;
    private String concepto;
    private double monto;
    @Column(name = "monto_mora")
    @JsonProperty("montoMora")
    private double montoMora;
    @Column(name = "subTotal")
    @JsonProperty("subTotal")
    private double subTotal;

    @JsonIgnore
    @MapsId("codPago")
    @ManyToOne
    @JoinColumn(name = "cod_pago")
    private Pago pago;

    public DetallePago(){}

    public DetallePago(Long codDetallePago, String concepto, double monto, double montoMora, double subTotal, Pago pago) {
        this.codDetallePago = codDetallePago;
        this.concepto = concepto;
        this.monto = monto;
        this.montoMora = montoMora;
        this.subTotal = subTotal;
        this.pago = pago;
    }

    public Long getCodDetallePago() {
        return codDetallePago;
    }

    public void setCodDetallePago(Long codDetallePago) {
        this.codDetallePago = codDetallePago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMontoMora() {
        return montoMora;
    }

    public void setMontoMora(double montoMora) {
        this.montoMora = montoMora;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
