package com.longdrink.rest_api.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class InsertPago implements Serializable {
    private Long codAlumno;
    //Pago.
    private String descripcion;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaPago;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    private Date fechaVencimiento;
    private double total;
    //Detalle Pago.
    private String concepto;
    private double monto;
    private double montoMora;
    private double subTotal;

    public InsertPago(){}

    public InsertPago(Long codAlumno, String descripcion, Date fechaPago, Date fechaVencimiento, double total, String concepto, double monto, double montoMora, double subTotal) {
        this.codAlumno = codAlumno;
        this.descripcion = descripcion;
        this.fechaPago = fechaPago;
        this.fechaVencimiento = fechaVencimiento;
        this.total = total;
        this.concepto = concepto;
        this.monto = monto;
        this.montoMora = montoMora;
        this.subTotal = subTotal;
    }

    @JsonIgnore
    public InsertPago limpiarDatos(){
        try{
            return new InsertPago(this.codAlumno,this.descripcion.trim().toUpperCase(),
                    this.fechaPago,this.fechaVencimiento,
                    this.total,this.concepto.trim().toUpperCase(),
                    this.monto,this.montoMora,this.subTotal);
        }catch(Exception ex){ return null; }
    }

    @JsonIgnore
    public boolean validarDatos(){
        return this.descripcion.length() >=1 && this.descripcion.length() <= 254 &&
                this.total >=(double)1 && this.total <= (double)3000 && this.concepto.length() >= 1 &&
                this.concepto.length() <= 254 && this.monto >= (double)1 && this.monto <= (double)1000 &&
                this.montoMora >= (double)0 && this.montoMora <= (double)1000 && this.subTotal >= (double)1 && this.subTotal <= (double)3000;
    }

    public Long getCodAlumno() {
        return codAlumno;
    }

    public void setCodAlumno(Long codAlumno) {
        this.codAlumno = codAlumno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
}
