package com.longdrink.rest_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Pago")
@Table(name = "pago")
public class Pago implements Serializable {
    @Id
    @Column(name = "cod_pago")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("codPago")
    private Long codPago;
    @Column(name = "fecha_pago")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaPago")
    private Date fechaPago;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT-5")
    @JsonProperty("fechaVencimiento")
    private Date fechaVencimiento;
    private boolean estado;
    private String descripcion;
    private double total;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cod_alumno")
    private Alumno alumno;

    @OneToMany(mappedBy = "pago")
    private List<DetallePago> detallePagos;

    public Pago(){}

    public Pago(Long codPago, Date fechaPago, Date fechaVencimiento, boolean estado, String descripcion, double total, Alumno alumno, List<DetallePago> detallePagos) {
        this.codPago = codPago;
        this.fechaPago = fechaPago;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.descripcion = descripcion;
        this.total = total;
        this.alumno = alumno;
        this.detallePagos = detallePagos;
    }

    public Pago(Long codPago, Date fechaPago, Date fechaVencimiento, boolean estado, String descripcion, double total, Alumno alumno) {
        this.codPago = codPago;
        this.fechaPago = fechaPago;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.descripcion = descripcion;
        this.total = total;
        this.alumno = alumno;
    }

    public Long getCodPago() {
        return codPago;
    }

    public void setCodPago(Long codPago) {
        this.codPago = codPago;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<DetallePago> getDetallePagos() {
        return detallePagos;
    }

    public void setDetallePagos(List<DetallePago> detallePagos) {
        this.detallePagos = detallePagos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
