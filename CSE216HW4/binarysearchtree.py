class Node:
    element = None
    left = None
    right = None

    def __init__ (self):
        self.element = None

    def setData(self, element):
        self.element = element
    
    def setLeft(self, leftNode):
        self.left = leftNode

    def setRight(self, rightNode):
        self.right = rightNode

    def inorderNode(root , list):
        if(root.left != None):
            Node.inorderNode(root.left, list)
        
        list.append(root)

        if(root.right != None):
            Node.inorderNode(root.right,list)

        return list


    def __iter__(self):
        node = self
        listToIterate = Node.inorderNode(root = node,list=[])

        for element in listToIterate:
            yield element.element

        return

    def __str__(self):
        return str(self.element)

class BinarySearchTree:
    root = None
    name = None


    def __init__(self, name, root):
        self.name = name
        self.root = root

    def setRoot(self, root):
        self.root = root

    def add(self, node):
        ## TREE DOES NOT TAKE DUPLICATE VALUES - NOT SPECIFIED IN INSTRUCTIONS
        if(node == None):
            return
        
        if(self.root.element == None):
            self.root = node


        self.addHelper(self.root, node)

    
    def addHelper(self, traverse, node):
        ## TREE DOES NOT TAKE DUPLICATE VALUES - NOT SPECIFIED IN INSTRUCTIONS
        if(node.element < traverse.element):
            if(traverse.left == None):
                traverse.left = node
            else:
                self.addHelper(traverse.left, node)

        if(node.element > traverse.element):
            if(traverse.right == None):
                traverse.right = node
            else:
                self.addHelper(traverse.right, node)

    
    def add_all(self, *list):
        ## TREE DOES NOT TAKE DUPLICATE VALUES - NOT SPECIFIED IN INSTRUCTIONS
        for element in list:
            node = Node()
            node.element = element
            self.add(node)

        return

    def inorder(self, root, list):
        if(root.left != None):
            self.inorder(root.left, list)

        list.append(root)

        if(root.right != None):
            self.inorder(root.right, list)

        return list


    def inorderElements(self, root, list):
        if(root.left != None):
            self.inorderElements(root.left, list)

        list.append(root.element)

        if(root.right != None):
            self.inorderElements(root.right, list)

        return list


    def inorderString(self, node, concat):
        
        concat = "" + concat + str(node.element) + ""

        if(node.left != None):
            concat = self.inorderString(node.left, concat + "L:(")
            concat += ")"
        
        if(node.right != None):
            concat = self.inorderString(node.right, concat + "R:(")
            concat += ")"

        return concat


    def __str__(self):
        list = self.inorder(self.root, [])

        if(self.root == None):
            return "Null bruh"

        toReturn = "[" + self.name + "] " + self.inorderString(self.root, "")

        return toReturn

    def __iter__(self):
        listToIterate = self.inorder(self.root,[])

        for node in listToIterate:
            yield node.element

        return
        



class Merger:
    def __init__(self):
        return

    
    def merge(t1, t2):
        mergedBST = BinarySearchTree("Merged Tree", root = Node())
        t1Iterator = t1.__iter__()
        t2Iterator = t2.__iter__()

        t1Done = False
        t2Done = False

        while(not t1Done or not t2Done):
            try:
                element = next(t1Iterator)
                node = Node()
                node.element = element
                mergedBST.add(node)
            except StopIteration:
                t1Done = True

            try:
                element = next(t2Iterator)
                node = Node()
                node.element = element
                mergedBST.add(node)
            except StopIteration:
                t2Done = True

        return mergedBST.inorderElements(mergedBST.root,[])


if __name__ == "__main__":
    tree = BinarySearchTree(name="Oak", root = Node())
    tree.add_all(5, 3, 9, 0) # adds the elements in the order 5, 3, 9, and then 0
    print(tree)
    

    t1 = BinarySearchTree(name="Oak", root=Node())
    t2 = BinarySearchTree(name="Birch", root=Node())
    t1.add_all(5, 3, 9, 0)
    t2.add_all(1, 0, 10, 2, 7)

    for x in t1:
        print(x)


    for x in t2.root:
        print(x)

    print(Merger.merge(t1, t2))