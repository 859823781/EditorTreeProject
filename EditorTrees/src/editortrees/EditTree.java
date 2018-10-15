package editortrees;

import editortrees.Node.Code;

// A height-balanced binary tree with rank that could be the basis for a text editor.

public class EditTree {
	private Node root;
	int n;
	DisplayableBinaryTree display;
	Node last = Node.NULL_NODE;

	/**
	 * MILESTONE 1 Construct an empty tree
	 */
	public EditTree() {
		this.root = Node.NULL_NODE;
		this.root.parent = Node.NULL_NODE;
		this.root.balance = Code.SAME;
		n = 0;
	}

	/**
	 * MILESTONE 1 Construct a single-node tree whose element is ch
	 * 
	 * @param ch
	 */
	public EditTree(char ch) {
		this.root = new Node(ch, this);
		this.root.parent = Node.NULL_NODE;
		this.root.balance = Code.SAME;
		n = 0;

	}

	/**
	 * MILESTONE 2 Make this tree be a copy of e, with all new nodes, but the
	 * same shape and contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		this.root = e.root.copy(e.getRoot(), e);
	}

	/**
	 * MILESTONE 3 Create an EditTree whose toString is s. This can be done in
	 * O(N) time, where N is the size of the tree (note that repeatedly calling
	 * insert() would be O(N log N), so you need to find a more efficient way to
	 * do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		this.root = Node.NULL_NODE;
		this.root = this.root.stringConstruct(0, s.length(), s, this);

	}

	/**
	 * MILESTONE 1 returns the total number of rotations done in this tree since
	 * it was created. A double rotation counts as two.
	 *
	 * @return number of rotations since this tree was created.
	 */
	public int totalRotationCount() {
		return n; // replace by a real calculation.
	}

	/**
	 * MILESTONE 1 return the string produced by an inorder traversal of this
	 * tree
	 */
	@Override
	public String toString() {

		return this.root.toString();

	}

	/**
	 * MILESTONE 1 This one asks for more info from each node. You can write it
	 * like the arraylist-based toString() method from the BinarySearchTree
	 * assignment. However, the output isn't just the elements, but the
	 * elements, ranks, and balance codes. Former CSSE230 students recommended
	 * that this method, while making it harder to pass tests initially, saves
	 * them time later since it catches weird errors that occur when you don't
	 * update ranks and balance codes correctly. For the tree with root b and
	 * children a and c, it should return the string: [b1=, a0=, c0=] There are
	 * many more examples in the unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in a
	 *         pre-order traversal of the tree.
	 */
	public String toDebugString() {
		// TODO
		String s = root.toDebugString();
		if (s == "") {
			return "[]";
		}
		return "[" + s.substring(0, s.length() - 2) + "]";
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch
	 *            character to add to the end of this tree.
	 */
	public void add(char ch) {
		// Notes:
		// 1. Please document chunks of code as you go. Why are you doing what
		// you are doing? Comments written after the code is finalized tend to
		// be useless, since they just say WHAT the code does, line by line,
		// rather than WHY the code was written like that. Six months from now,
		// it's the reasoning behind doing what you did that will be valuable to
		// you!
		// 2. Unit tests are cumulative, and many things are based on add(), so
		// make sure that you get this one correct.
		if (this.root == Node.NULL_NODE) {
			this.root = new Node(ch, this);
		} else {
			this.n += this.root.add(ch, this);
		}
		if (this.root.parent != Node.NULL_NODE) {
			this.root = this.root.parent;
		}
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param ch
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             if pos is negative or too large for this tree
	 */
	public void add(char ch, int pos) throws IndexOutOfBoundsException {
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (this.root == Node.NULL_NODE && pos == 0) {
			this.root = new Node(ch, this);
		} else if (this.root == Node.NULL_NODE && pos != 0) {
			throw new IndexOutOfBoundsException();
		} else {
			this.root.add(ch, pos, 0);
		}
		while (this.root.parent != Node.NULL_NODE) {
			this.root = this.root.parent;
		}
	}

	/**
	 * MILESTONE 1
	 * 
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		// TODO
		return root.get(pos, 0);
	}

	/**
	 * MILESTONE 1
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		return root.height(); // replace by a real calculation.
	}

	/**
	 * MILESTONE 2
	 * 
	 * @return the number of nodes in this tree, not counting the NULL_NODE if
	 *         you have one.
	 */
	public int size() {
		return root.size(); // replace by a real calculation.
	}

	/**
	 * MILESTONE 2
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		if (pos < 0 || pos > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		Node temp = new Node();
		Character toReturn = this.get(pos);
		char[] deleted = new char[1];
		this.root = this.root.delete(pos, this, deleted);
		if (this.root != Node.NULL_NODE) {
			// //root sometimes will be wrong for some reasons,
			// //here we used an inefficent way to make it correct.
			// //we will change it later if we come up with any idea.
			if (this.root.left.height() == this.root.right.height() && this.root.left != Node.NULL_NODE
					&& this.root.right != Node.NULL_NODE) {
				this.root.balance = Code.SAME;
			}
		}
		return toReturn; // replace by a real calculation.
	}

	/**
	 * MILESTONE 3, EASY This method operates in O(length*log N), where N is the
	 * size of this tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(this.get(pos + i));
		}
		return sb.toString();
	}

	/**
	 * MILESTONE 3, MEDIUM - SEE THE PAPER REFERENCED IN THE SPEC FOR ALGORITHM!
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		// if (this.equals(other)) {
		// throw new IllegalArgumentException();
		// }
		// String str = "";
		// str=str+this.toString();
		// str=str+other.toString();
		// other.root=Node.NULL_NODE;
		// EditTree t = new EditTree();
		// this.root=t.root;
		// for(int i = 0;i<str.length();i++){
		// this.add(str.charAt(i));
		// }
		Node current = Node.NULL_NODE;
		int h1 = this.height();
		int h2 = other.height();
		if (this.equals(other)) {
			throw new IllegalArgumentException();
		}
		if (h1 >= h2) {
			current = other.root;
		} else {
			current = this.root;
		}
		if (this.root.equals(Node.NULL_NODE)) {
			this.root = other.root;
			other.root = Node.NULL_NODE;
			return;
		}
		if (other.root == Node.NULL_NODE) {
			return;
		}

		if (!current.equals(Node.NULL_NODE)) {
			if (h1 == h2) {
				while (current.left != Node.NULL_NODE) {
					current = current.left;
				}
				Node k = new Node();
				k.element = other.delete(0);
				k.left = this.root;
				this.root.parent = k;
				int n = this.root.size();
				k.right = other.root;
				other.root.parent = k;
				this.root = k;
				k.rank = n;
				k.tree = this;
				other.root = Node.NULL_NODE;
			} else if (h1 > h2) {
				current = other.root;
				while (current.left != Node.NULL_NODE) {
					current = current.left;
				}
				other.delete(0);
				pasteHB(this, current, other, true);
			} else {
				current = this.root;
				while (current.right != Node.NULL_NODE) {
					current = current.right;
				}
				this.delete(this.size() - 1);
				pasteHB(other, current, this, false);
			}
		}
		other.root = Node.NULL_NODE;
		// if(h1 > h2 + 1){
		// EditTree L = this;
		// char k = other.delete(0);
		// EditTree G = other;
		// pasteR(L,k,G);
		// }
		// other.root = Node.NULL_NODE;
	}

	// public void pasteR(EditTree L,char k, EditTree G){
	// Node current = L.root;
	// int h1 = L.height();
	// int h2 = G.height();
	// while(current != Node.NULL_NODE){
	// if(h1 >= h2 + 2 && h1-1 <= h2 + 1){
	// break;
	// }
	// current = current.right;
	// }
	// Node p = current;
	// Node q = new Node(k,this);
	//
	// if((p.right.height()==h2+1 && p.left.height()==h2+2) || (p.right.height()
	// == h2 && p.left.height()==h2+1)){
	// q.left = p.right;
	// p.right.parent = q.left;
	// q.right = G.root;
	// p.right = q;
	// q.parent = p;
	// }else if(p.right.height() == h2+1 && p.left.height() == h2){
	// q.left = p.right;
	// p.right.parent = q.left;
	// q.right = G.root;
	// p.right = q;
	// q.parent = p;
	// p.doubleleft(true);
	// }else if(p.right.height() == h2+1 && p.left.height() == h2+1){
	// q.left = p.right;
	// p.right.parent = q.left;
	// q.right = G.root;
	// p.right = q;
	// q.parent = p;
	//
	// }
	//
	// }

	public void pasteHB(EditTree T, Node q, EditTree V, boolean taller) {
		int h1 = T.height();
		int h2 = V.height();
		Node current = T.root;
		Node previous = Node.NULL_NODE;
		if (taller == true) {
			if (current != Node.NULL_NODE) {
				while (current.right != Node.NULL_NODE) {
					if (current.balance.equals(Code.LEFT)) {
						h1 -= 2;
					} else {
						h1--;
					}
					previous = current;
					current = current.right;
					if (h1 - h2 <= 1) {
						break;
					}
				}
			}
			q.left = current;
			q.rank = current.size();
			q.right = V.root;
			previous.right = q;
			if (h1 == h2) {
				q.balance = Code.SAME;
			} else {
				q.balance = Code.RIGHT;
			}
			current = current.parent;
			current.right = q;
			q.parent = current;
			q.left.parent = q;

			if (current.balance == Code.RIGHT) {
				if (current.parent == Node.NULL_NODE) {
					this.root = current.doubleleft(false);
				} else {
					Node parent = current.parent;
					parent.right = current.doubleleft(false);
				}
				return;
			}
			while (current.parent != Node.NULL_NODE) {
				current = current.parent;
				if (current.balance == Code.RIGHT) {
					current = current.singleLeft(false);
				}
			}

		} else {
			if (current != Node.NULL_NODE) {
				while (current.left != Node.NULL_NODE) {
					if (current.balance.equals(Code.RIGHT)) {
						h1 -= 2;
					} else {
						h1--;
					}
					previous = current;
					current = current.left;
					if (h1 - h2 <= 1) {
						break;
					}
				}
			}
			q.right = current;
			q.left = V.root;
			q.rank = V.root.size();
			previous.left = q;
			previous.rank = q.size();
		}
		if (h1 == h2) {
			q.balance = Code.SAME;
		} else {
			q.balance = Code.LEFT;
		}
		current = current.parent;
		current.left = q;
		q.parent = current;
		q.right.parent = q;

		if (current.balance == Code.LEFT) {
			if (current.parent == Node.NULL_NODE) {
				this.root = current.doubleRight(false);
			} else {
				Node parent = current.parent;
				parent.left = current.doubleRight(false);
			}
			this.root = T.root;
			return;
		}

		this.root = T.root;
		while (current != Node.NULL_NODE) {
			current = current.parent;
			if (current.balance == Code.RIGHT) {
				current = current.singleRight(false);
			}
		}

	}

	/**
	 * MILESTONE 3: DIFFICULT This operation must be done in time proportional
	 * to the height of this tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		String tree = this.toString();
		String former = tree.substring(0, pos);
		String late = tree.substring(pos, tree.length());
		EditTree n = new EditTree(late);
		EditTree o = new EditTree(former);
		this.root=o.root;
		return n; // replace by a real calculation.
	}

	/**
	 * MILESTONE 3: JUST READ IT FOR USE OF SPLIT/CONCATENATE This method is
	 * provided for you, and should not need to be changed. If split() and
	 * concatenate() are O(log N) operations as required, delete should also be
	 * O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length) throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete" : "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * MILESTONE 3 Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		// convert the tree to string and then compare the string with given s
		if (s.isEmpty()) {
			return 0;
		}
		String str = this.toString();
		for (int i = 0; i < str.length() - s.length() + 1; i++) {
			if (str.substring(i, i + s.length()).equals(s)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * MILESTONE 3
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		// similar idea to first find function, but begin from number pos
		// element of string
		if (s.isEmpty()) {
			return 0;
		}
		String str = this.toString();
		for (int i = pos; i < this.size() - s.length() + 1; i++) {
			if (str.substring(i, i + s.length()).equals(s)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	public void setLast(Node n) {
		this.last = n;
		// System.out.println(this.last.parent.element);
	}

	public int slowSize() {
		// TODO Auto-generated method stub.
		return this.root.size();
	}

	public int slowHeight() {
		// TODO Auto-generated method stub.
		return root.height();
	}

	public void show() {
		if (this.display == null) {
			this.display = new DisplayableBinaryTree(this, 960, 1080, true);
		} else {
			this.display.show(true);
		}
	}

	public void close() {
		if (this.display != null) {
			this.display.close();
		}
	}
}
