package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedGraph {

	/**
	 * ����
	 * 
	 * @author zhangyuchen
	 * 
	 */
	public class Vertex {
		// ��������
		private int				index;
		// ���㱣��Ķ�������
		private Object			data;
		// ��������ı�
		private List<Integer>	edge;
		// ����ʱ�ã�������ɫ:white, gray, black
		private String			color	= "white";
		// ����ʱ�ã�����Դ��Ķ���
		private int				degree	= 0;
		// ����ʱ�ã�������
		private Vertex			parent	= null;

		public void link(Vertex other) {
			if (other == null) {
				return;
			}
			this.edge.add(other.index - 1);
		}

		public int getIndex() {
			return this.index;
		}

		public Object getData() {
			return this.data;
		}
	}

	List<Vertex>	vertexes	= new ArrayList<LinkedGraph.Vertex>();	// ���㼯��

	public Vertex addVertex(Object data) {
		Vertex v = new Vertex();
		v.data = data;
		this.vertexes.add(v);
		v.index = this.vertexes.size();
		v.edge = new LinkedList<Integer>();
		return v;
	}

	public static enum SearchType {
		BFS, // breadth first search
		DFS, // depth first search
	}

	public static interface Walk {
		/**
		 * ����ÿ������
		 * 
		 * @param v
		 *            ��ǰ�������Ķ���
		 * @return �Ƿ��������
		 */
		boolean walk(Vertex v);
	}

	public void serach(Vertex src, SearchType type, Walk walk) {
		if (type == null || walk == null) {
			return;
		}
		if (vertexes.size() < 1) {
			return;
		}
		if (src == null) {
			src = vertexes.get(0);
		}
		switch (type) {
			case BFS:
				this.BFS(src, walk);
				break;

			case DFS:
				this.DFS(src, walk);
				break;

			default:
				break;
		}
	}

	private void BFS(Vertex src, Walk walk) {
		for (Vertex v : vertexes) {
			v.color = "white";
			v.degree = Integer.MAX_VALUE;
			v.parent = null;
		}
		src.color = "gray";
		src.degree = 0;
		src.parent = null;

		if (!walk.walk(src)) {
			return;
		}

		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		queue.offerLast(src);

		while (queue.size() > 0) {
			Vertex u = queue.poll();
			for (Integer i : u.edge) {
				Vertex v = vertexes.get(i);
				if (v.color.equals("white")) {
					boolean goon = walk.walk(v);
					v.color = "gray";
					v.degree = u.degree + 1;
					v.parent = u;
					if (!goon) {
						return;
					}
					queue.offerLast(v);
				}
			}
			u.color = "black";
		}
	}

	private void DFS(Vertex src, Walk walk) {
		for (Vertex v : vertexes) {
			v.color = "white";
			v.parent = null;
		}

		if (!walk.walk(src)) {
			return;
		}
		DFSVisit(src, walk);
	}

	private void DFSVisit(Vertex u, Walk walk) {
		u.color = "gray";
		for (Integer i : u.edge) {
			Vertex v = vertexes.get(i);
			if (v.color.equals("white")) {
				v.parent = u;
				if (!walk.walk(v)) {
					return;
				}
				DFSVisit(v, walk);
			}
		}
		u.color = "black";
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (Vertex v : vertexes) {
			if (v == null) {
				continue;
			}
			out.append(v.index).append("(").append(v.getData()).append(")");
			for (Integer e : v.edge) {
				out.append(" -> ").append(e + 1);
			}
			out.append("\n");
		}
		return out.toString();
	}

}
