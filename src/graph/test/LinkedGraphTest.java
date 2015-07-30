package graph.test;

import graph.LinkedGraph;
import graph.LinkedGraph.SearchType;
import graph.LinkedGraph.Vertex;
import graph.LinkedGraph.Walk;

import java.util.LinkedList;

public class LinkedGraphTest {

	public static void main(String[] args) {
		LinkedGraphTest t = new LinkedGraphTest();
		t.buildAGraph();

		t.testSearch();
	}

	private LinkedGraph	g	= null;

	private void buildAGraph() {
		LinkedGraph g = new LinkedGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		Vertex d = g.addVertex("D");
		Vertex e = g.addVertex("E");
		Vertex f = g.addVertex("F");
		Vertex g2 = g.addVertex("G");
		Vertex h = g.addVertex("H");

		a.link(b);
		a.link(c);
		b.link(d);
		d.link(a);
		d.link(e);
		e.link(f);
		f.link(g2);
		g2.link(h);
		h.link(g2);

		this.g = g;
	}

	public void testSearch() {
		System.out.println(g);

		final LinkedList<Vertex> vertexes = new LinkedList<Vertex>();

		g.serach(null, SearchType.BFS, new Walk() {
			@Override
			public boolean walk(Vertex v) {
				System.out.print(" -> " + v.getData());
				if ("D".equals(v.getData())) {
					vertexes.offerLast(v);
				}
				return true;
			}
		});

		System.out.println();

		g.serach(vertexes.peek(), SearchType.BFS, new Walk() {
			@Override
			public boolean walk(Vertex v) {
				System.out.print(" -> " + v.getData());
				if ("G".equals(v.getData())) {
					return false;
				}
				return true;
			}
		});
		
		System.out.println();
		
		g.serach(null, SearchType.DFS, new Walk() {
			@Override
			public boolean walk(Vertex v) {
				System.out.print(" -> " + v.getData());
				return true;
			}
		});
		
		System.out.println();
		
		g.serach(vertexes.peek(), SearchType.DFS, new Walk() {
			@Override
			public boolean walk(Vertex v) {
				System.out.print(" -> " + v.getData());
				return true;
			}
		});
	}

}
