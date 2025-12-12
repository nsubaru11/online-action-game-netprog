package model;

/**
 * 物体の抽象クラスです。
 */
public abstract class Entity {
	/** 物体の位置を表すベクトル */
	private Vector2D position;
	/** 物体の速度を表すベクトル */
	private Vector2D velocity;
	/** 物体の幅方向を表すベクトル（positionを始点とするベクトル） */
	private Vector2D width;
	/** 物体の高さ方向を表すベクトル（positionを始点とするベクトル） */
	private Vector2D height;

}
