package br.com.algotrade.entidades;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name="historico_geral")
public class HistoricoGeral implements Cloneable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_historico")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date data = new Date();
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="fk_historico_ativo_id")
	private Ativo ativo;
	
	@Column
	BigDecimal minuto_1, minuto_2, minuto_3, minuto_4, minuto_5, minuto_6, minuto_7,
	 minuto_8, minuto_9, minuto_10, minuto_11, minuto_12, minuto_13, minuto_14,
	 minuto_15, minuto_16, minuto_17, minuto_18, minuto_19, minuto_20, minuto_21,
	 minuto_22, minuto_23, minuto_24, minuto_25, minuto_26, minuto_27, minuto_28,
	 minuto_29, minuto_30, minuto_31, minuto_32, minuto_33, minuto_34, minuto_35,
	 minuto_36, minuto_37, minuto_38, minuto_39, minuto_40, minuto_41, minuto_42,
	 minuto_43, minuto_44, minuto_45, minuto_46, minuto_47, minuto_48, minuto_49,
	 minuto_50, minuto_51, minuto_52, minuto_53, minuto_54, minuto_55, minuto_56,
	 minuto_57, minuto_58, minuto_59, minuto_60, minuto_61, minuto_62, minuto_63,
	 minuto_64, minuto_65, minuto_66, minuto_67, minuto_68, minuto_69, minuto_70,
	 minuto_71, minuto_72, minuto_73, minuto_74, minuto_75, minuto_76, minuto_77,
	 minuto_78, minuto_79, minuto_80, minuto_81, minuto_82, minuto_83, minuto_84;
	
	public HistoricoGeral() {
	}
	
    public HistoricoGeral clone() throws CloneNotSupportedException {
            return (HistoricoGeral) super.clone();
    }
    
	//Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getMinuto_1() {
		return minuto_1;
	}

	public void setMinuto_1(BigDecimal minuto_1) {
		this.minuto_1 = minuto_1;
	}

	public BigDecimal getMinuto_2() {
		return minuto_2;
	}

	public void setMinuto_2(BigDecimal minuto_2) {
		this.minuto_2 = minuto_2;
	}

	public BigDecimal getMinuto_3() {
		return minuto_3;
	}

	public void setMinuto_3(BigDecimal minuto_3) {
		this.minuto_3 = minuto_3;
	}

	public BigDecimal getMinuto_4() {
		return minuto_4;
	}

	public void setMinuto_4(BigDecimal minuto_4) {
		this.minuto_4 = minuto_4;
	}

	public BigDecimal getMinuto_5() {
		return minuto_5;
	}

	public void setMinuto_5(BigDecimal minuto_5) {
		this.minuto_5 = minuto_5;
	}

	public BigDecimal getMinuto_6() {
		return minuto_6;
	}

	public void setMinuto_6(BigDecimal minuto_6) {
		this.minuto_6 = minuto_6;
	}

	public BigDecimal getMinuto_7() {
		return minuto_7;
	}

	public void setMinuto_7(BigDecimal minuto_7) {
		this.minuto_7 = minuto_7;
	}

	public BigDecimal getMinuto_8() {
		return minuto_8;
	}

	public void setMinuto_8(BigDecimal minuto_8) {
		this.minuto_8 = minuto_8;
	}

	public BigDecimal getMinuto_9() {
		return minuto_9;
	}

	public void setMinuto_9(BigDecimal minuto_9) {
		this.minuto_9 = minuto_9;
	}

	public BigDecimal getMinuto_10() {
		return minuto_10;
	}

	public void setMinuto_10(BigDecimal minuto_10) {
		this.minuto_10 = minuto_10;
	}

	public BigDecimal getMinuto_11() {
		return minuto_11;
	}

	public void setMinuto_11(BigDecimal minuto_11) {
		this.minuto_11 = minuto_11;
	}

	public BigDecimal getMinuto_12() {
		return minuto_12;
	}

	public void setMinuto_12(BigDecimal minuto_12) {
		this.minuto_12 = minuto_12;
	}

	public BigDecimal getMinuto_13() {
		return minuto_13;
	}

	public void setMinuto_13(BigDecimal minuto_13) {
		this.minuto_13 = minuto_13;
	}

	public BigDecimal getMinuto_14() {
		return minuto_14;
	}

	public void setMinuto_14(BigDecimal minuto_14) {
		this.minuto_14 = minuto_14;
	}

	public BigDecimal getMinuto_15() {
		return minuto_15;
	}

	public void setMinuto_15(BigDecimal minuto_15) {
		this.minuto_15 = minuto_15;
	}

	public BigDecimal getMinuto_16() {
		return minuto_16;
	}

	public void setMinuto_16(BigDecimal minuto_16) {
		this.minuto_16 = minuto_16;
	}

	public BigDecimal getMinuto_17() {
		return minuto_17;
	}

	public void setMinuto_17(BigDecimal minuto_17) {
		this.minuto_17 = minuto_17;
	}

	public BigDecimal getMinuto_18() {
		return minuto_18;
	}

	public void setMinuto_18(BigDecimal minuto_18) {
		this.minuto_18 = minuto_18;
	}

	public BigDecimal getMinuto_19() {
		return minuto_19;
	}

	public void setMinuto_19(BigDecimal minuto_19) {
		this.minuto_19 = minuto_19;
	}

	public BigDecimal getMinuto_20() {
		return minuto_20;
	}

	public void setMinuto_20(BigDecimal minuto_20) {
		this.minuto_20 = minuto_20;
	}

	public BigDecimal getMinuto_21() {
		return minuto_21;
	}

	public void setMinuto_21(BigDecimal minuto_21) {
		this.minuto_21 = minuto_21;
	}

	public BigDecimal getMinuto_22() {
		return minuto_22;
	}

	public void setMinuto_22(BigDecimal minuto_22) {
		this.minuto_22 = minuto_22;
	}

	public BigDecimal getMinuto_23() {
		return minuto_23;
	}

	public void setMinuto_23(BigDecimal minuto_23) {
		this.minuto_23 = minuto_23;
	}

	public BigDecimal getMinuto_24() {
		return minuto_24;
	}

	public void setMinuto_24(BigDecimal minuto_24) {
		this.minuto_24 = minuto_24;
	}

	public BigDecimal getMinuto_25() {
		return minuto_25;
	}

	public void setMinuto_25(BigDecimal minuto_25) {
		this.minuto_25 = minuto_25;
	}

	public BigDecimal getMinuto_26() {
		return minuto_26;
	}

	public void setMinuto_26(BigDecimal minuto_26) {
		this.minuto_26 = minuto_26;
	}

	public BigDecimal getMinuto_27() {
		return minuto_27;
	}

	public void setMinuto_27(BigDecimal minuto_27) {
		this.minuto_27 = minuto_27;
	}

	public BigDecimal getMinuto_28() {
		return minuto_28;
	}

	public void setMinuto_28(BigDecimal minuto_28) {
		this.minuto_28 = minuto_28;
	}

	public BigDecimal getMinuto_29() {
		return minuto_29;
	}

	public void setMinuto_29(BigDecimal minuto_29) {
		this.minuto_29 = minuto_29;
	}

	public BigDecimal getMinuto_30() {
		return minuto_30;
	}

	public void setMinuto_30(BigDecimal minuto_30) {
		this.minuto_30 = minuto_30;
	}

	public BigDecimal getMinuto_31() {
		return minuto_31;
	}

	public void setMinuto_31(BigDecimal minuto_31) {
		this.minuto_31 = minuto_31;
	}

	public BigDecimal getMinuto_32() {
		return minuto_32;
	}

	public void setMinuto_32(BigDecimal minuto_32) {
		this.minuto_32 = minuto_32;
	}

	public BigDecimal getMinuto_33() {
		return minuto_33;
	}

	public void setMinuto_33(BigDecimal minuto_33) {
		this.minuto_33 = minuto_33;
	}

	public BigDecimal getMinuto_34() {
		return minuto_34;
	}

	public void setMinuto_34(BigDecimal minuto_34) {
		this.minuto_34 = minuto_34;
	}

	public BigDecimal getMinuto_35() {
		return minuto_35;
	}

	public void setMinuto_35(BigDecimal minuto_35) {
		this.minuto_35 = minuto_35;
	}

	public BigDecimal getMinuto_36() {
		return minuto_36;
	}

	public void setMinuto_36(BigDecimal minuto_36) {
		this.minuto_36 = minuto_36;
	}

	public BigDecimal getMinuto_37() {
		return minuto_37;
	}

	public void setMinuto_37(BigDecimal minuto_37) {
		this.minuto_37 = minuto_37;
	}

	public BigDecimal getMinuto_38() {
		return minuto_38;
	}

	public void setMinuto_38(BigDecimal minuto_38) {
		this.minuto_38 = minuto_38;
	}

	public BigDecimal getMinuto_39() {
		return minuto_39;
	}

	public void setMinuto_39(BigDecimal minuto_39) {
		this.minuto_39 = minuto_39;
	}

	public BigDecimal getMinuto_40() {
		return minuto_40;
	}

	public void setMinuto_40(BigDecimal minuto_40) {
		this.minuto_40 = minuto_40;
	}

	public BigDecimal getMinuto_41() {
		return minuto_41;
	}

	public void setMinuto_41(BigDecimal minuto_41) {
		this.minuto_41 = minuto_41;
	}

	public BigDecimal getMinuto_42() {
		return minuto_42;
	}

	public void setMinuto_42(BigDecimal minuto_42) {
		this.minuto_42 = minuto_42;
	}

	public BigDecimal getMinuto_43() {
		return minuto_43;
	}

	public void setMinuto_43(BigDecimal minuto_43) {
		this.minuto_43 = minuto_43;
	}

	public BigDecimal getMinuto_44() {
		return minuto_44;
	}

	public void setMinuto_44(BigDecimal minuto_44) {
		this.minuto_44 = minuto_44;
	}

	public BigDecimal getMinuto_45() {
		return minuto_45;
	}

	public void setMinuto_45(BigDecimal minuto_45) {
		this.minuto_45 = minuto_45;
	}

	public BigDecimal getMinuto_46() {
		return minuto_46;
	}

	public void setMinuto_46(BigDecimal minuto_46) {
		this.minuto_46 = minuto_46;
	}

	public BigDecimal getMinuto_47() {
		return minuto_47;
	}

	public void setMinuto_47(BigDecimal minuto_47) {
		this.minuto_47 = minuto_47;
	}

	public BigDecimal getMinuto_48() {
		return minuto_48;
	}

	public void setMinuto_48(BigDecimal minuto_48) {
		this.minuto_48 = minuto_48;
	}

	public BigDecimal getMinuto_49() {
		return minuto_49;
	}

	public void setMinuto_49(BigDecimal minuto_49) {
		this.minuto_49 = minuto_49;
	}

	public BigDecimal getMinuto_50() {
		return minuto_50;
	}

	public void setMinuto_50(BigDecimal minuto_50) {
		this.minuto_50 = minuto_50;
	}

	public BigDecimal getMinuto_51() {
		return minuto_51;
	}

	public void setMinuto_51(BigDecimal minuto_51) {
		this.minuto_51 = minuto_51;
	}

	public BigDecimal getMinuto_52() {
		return minuto_52;
	}

	public void setMinuto_52(BigDecimal minuto_52) {
		this.minuto_52 = minuto_52;
	}

	public BigDecimal getMinuto_53() {
		return minuto_53;
	}

	public void setMinuto_53(BigDecimal minuto_53) {
		this.minuto_53 = minuto_53;
	}

	public BigDecimal getMinuto_54() {
		return minuto_54;
	}

	public void setMinuto_54(BigDecimal minuto_54) {
		this.minuto_54 = minuto_54;
	}

	public BigDecimal getMinuto_55() {
		return minuto_55;
	}

	public void setMinuto_55(BigDecimal minuto_55) {
		this.minuto_55 = minuto_55;
	}

	public BigDecimal getMinuto_56() {
		return minuto_56;
	}

	public void setMinuto_56(BigDecimal minuto_56) {
		this.minuto_56 = minuto_56;
	}

	public BigDecimal getMinuto_57() {
		return minuto_57;
	}

	public void setMinuto_57(BigDecimal minuto_57) {
		this.minuto_57 = minuto_57;
	}

	public BigDecimal getMinuto_58() {
		return minuto_58;
	}

	public void setMinuto_58(BigDecimal minuto_58) {
		this.minuto_58 = minuto_58;
	}

	public BigDecimal getMinuto_59() {
		return minuto_59;
	}

	public void setMinuto_59(BigDecimal minuto_59) {
		this.minuto_59 = minuto_59;
	}

	public BigDecimal getMinuto_60() {
		return minuto_60;
	}

	public void setMinuto_60(BigDecimal minuto_60) {
		this.minuto_60 = minuto_60;
	}

	public BigDecimal getMinuto_61() {
		return minuto_61;
	}

	public void setMinuto_61(BigDecimal minuto_61) {
		this.minuto_61 = minuto_61;
	}

	public BigDecimal getMinuto_62() {
		return minuto_62;
	}

	public void setMinuto_62(BigDecimal minuto_62) {
		this.minuto_62 = minuto_62;
	}

	public BigDecimal getMinuto_63() {
		return minuto_63;
	}

	public void setMinuto_63(BigDecimal minuto_63) {
		this.minuto_63 = minuto_63;
	}

	public BigDecimal getMinuto_64() {
		return minuto_64;
	}

	public void setMinuto_64(BigDecimal minuto_64) {
		this.minuto_64 = minuto_64;
	}

	public BigDecimal getMinuto_65() {
		return minuto_65;
	}

	public void setMinuto_65(BigDecimal minuto_65) {
		this.minuto_65 = minuto_65;
	}

	public BigDecimal getMinuto_66() {
		return minuto_66;
	}

	public void setMinuto_66(BigDecimal minuto_66) {
		this.minuto_66 = minuto_66;
	}

	public BigDecimal getMinuto_67() {
		return minuto_67;
	}

	public void setMinuto_67(BigDecimal minuto_67) {
		this.minuto_67 = minuto_67;
	}

	public BigDecimal getMinuto_68() {
		return minuto_68;
	}

	public void setMinuto_68(BigDecimal minuto_68) {
		this.minuto_68 = minuto_68;
	}

	public BigDecimal getMinuto_69() {
		return minuto_69;
	}

	public void setMinuto_69(BigDecimal minuto_69) {
		this.minuto_69 = minuto_69;
	}

	public BigDecimal getMinuto_70() {
		return minuto_70;
	}

	public void setMinuto_70(BigDecimal minuto_70) {
		this.minuto_70 = minuto_70;
	}

	public BigDecimal getMinuto_71() {
		return minuto_71;
	}

	public void setMinuto_71(BigDecimal minuto_71) {
		this.minuto_71 = minuto_71;
	}

	public BigDecimal getMinuto_72() {
		return minuto_72;
	}

	public void setMinuto_72(BigDecimal minuto_72) {
		this.minuto_72 = minuto_72;
	}

	public BigDecimal getMinuto_73() {
		return minuto_73;
	}

	public void setMinuto_73(BigDecimal minuto_73) {
		this.minuto_73 = minuto_73;
	}

	public BigDecimal getMinuto_74() {
		return minuto_74;
	}

	public void setMinuto_74(BigDecimal minuto_74) {
		this.minuto_74 = minuto_74;
	}

	public BigDecimal getMinuto_75() {
		return minuto_75;
	}

	public void setMinuto_75(BigDecimal minuto_75) {
		this.minuto_75 = minuto_75;
	}

	public BigDecimal getMinuto_76() {
		return minuto_76;
	}

	public void setMinuto_76(BigDecimal minuto_76) {
		this.minuto_76 = minuto_76;
	}

	public BigDecimal getMinuto_77() {
		return minuto_77;
	}

	public void setMinuto_77(BigDecimal minuto_77) {
		this.minuto_77 = minuto_77;
	}

	public BigDecimal getMinuto_78() {
		return minuto_78;
	}

	public void setMinuto_78(BigDecimal minuto_78) {
		this.minuto_78 = minuto_78;
	}

	public BigDecimal getMinuto_79() {
		return minuto_79;
	}

	public void setMinuto_79(BigDecimal minuto_79) {
		this.minuto_79 = minuto_79;
	}

	public BigDecimal getMinuto_80() {
		return minuto_80;
	}

	public void setMinuto_80(BigDecimal minuto_80) {
		this.minuto_80 = minuto_80;
	}

	public BigDecimal getMinuto_81() {
		return minuto_81;
	}

	public void setMinuto_81(BigDecimal minuto_81) {
		this.minuto_81 = minuto_81;
	}

	public BigDecimal getMinuto_82() {
		return minuto_82;
	}

	public void setMinuto_82(BigDecimal minuto_82) {
		this.minuto_82 = minuto_82;
	}

	public BigDecimal getMinuto_83() {
		return minuto_83;
	}

	public void setMinuto_83(BigDecimal minuto_83) {
		this.minuto_83 = minuto_83;
	}

	public BigDecimal getMinuto_84() {
		return minuto_84;
	}

	public void setMinuto_84(BigDecimal minuto_84) {
		this.minuto_84 = minuto_84;
	}

}
