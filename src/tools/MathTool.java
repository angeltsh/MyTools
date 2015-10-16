package tools;

import java.math.BigDecimal;

public class MathTool {

	/** �� */
	public static double add(double d1, double d2) { // ���мӷ�����
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	/** �� */
	public static double sub(double d1, double d2) { // ���м�������
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	/** �� */
	public static double mul(double d1, double d2) { // ���г˷�����
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	/** �� */
	public static double div(double d1, double d2, int len) {// ���г�������
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/** �������� */
	public static double round(double d, int len) { // ������������
		// ����
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// �κ�һ�����ֳ���1����ԭ����
		// ROUND_HALF_UP��BigDecimal��һ��������
		// ��ʾ������������Ĳ���
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}