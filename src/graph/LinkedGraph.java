package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LinkedGraph {

	/**
	 * 顶点
	 * 
	 * @author zhangyuchen
	 * 
	 */
	public class Vertex {
		// 顶点索引
		private int				index;
		// 顶点保存的额外数据
		private Object			data;
		// 顶点关联的边
		private List<Integer>	edge;
		// 遍历时用，顶点颜色:white, gray, black
		private String			color	= "white";
		// 遍历时用，距离源点的度数
		private int				degree	= 0;
		// 遍历时用，父顶点
		@SuppressWarnings("unused")
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

	List<Vertex>	vertexes	= new ArrayList<LinkedGraph.Vertex>();	// 顶点集合

	public Vertex addVertex(Object data) {
		Vertex v = new Vertex();
		v.data = data;
		this.vertexes.add(v);
		v.index = this.vertexes.size();
		v.edge = new LinkedList<Integer>();
		return v;
	}

	public Vertex getFront() {
		if (this.vertexes.size() < 1) {
			return null;
		}
		return this.vertexes.get(0);
	}

	public int getVertexCount() {
		return this.vertexes.size();
	}

	public int getNextCount(Vertex v) {
		if (v == null) {
			return 0;
		}
		return v.edge.size();
	}

	public Vertex getNext(Vertex v, int i) {
		int l = this.getNextCount(v);
		if (i < 0 || i >= l) {
			return null;
		}
		for (Integer e : v.edge) {
			if (i == 0) {
				return this.vertexes.get(e);
			}
			i--;
		}
		return null;
	}

	public Vertex[] getNexts(Vertex v) {
		Vertex[] vertexes = new Vertex[this.getNextCount(v)];
		int i = 0;
		for (Integer e : v.edge) {
			vertexes[i] = this.vertexes.get(e);
			i++;
		}
		return vertexes;
	}

	public static enum SearchType {
		BFS, // breadth first search
		DFS, // depth first search
	}

	public static interface Walk {
		/**
		 * 遍历每个顶点
		 * 
		 * @param v
		 *            当前遍历到的顶点
		 * @return 是否继续遍历
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
