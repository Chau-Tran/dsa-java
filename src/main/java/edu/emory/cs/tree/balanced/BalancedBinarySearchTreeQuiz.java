/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.tree.balanced;

import edu.emory.cs.tree.BinaryNode;

/** @author Jinho D. Choi */
public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if(node == null) return;

        BinaryNode<T> parent = null;
        if (node.hasParent()) {parent = node.getParent();}
        else {return;}

        BinaryNode<T> grandparent = null;
        if(parent != null && parent.hasParent()) {grandparent = parent.getParent();}
        else {return;}

        BinaryNode<T> uncle = null;
        if(grandparent != null && grandparent.hasBothChildren() && grandparent.isRightChild(parent)) {uncle = grandparent.getLeftChild();}
        else{return;}

        if(parent != null && !parent.hasBothChildren()) { //node is only child
            if (uncle != null && !uncle.hasBothChildren() && (uncle.getRightChild() != null || uncle.getLeftChild() != null)) { //parent is right child && uncle has only one child
                if (parent.isLeftChild(node)) {//node is left child: V formation on the right
                    rotateRight(parent);
                }
                //node is a right child: linear formation on the right
                rotateLeft(grandparent);

                if (uncle.getRightChild() != null) { //uncle has a right child: V formation on the left
                    rotateLeft(uncle);
                }
                rotateRight(grandparent); //uncle has a left child: linear on the left
            }
        }
    }

//    public static void main(String[] args) {
//        BalancedBinarySearchTreeQuiz<Integer> tree = new BalancedBinarySearchTreeQuiz<>();
//        tree.add(7);
//        tree.add(5);
//        tree.add(4);
//        tree.add(2);
//        tree.add(1);
//        tree.add(3);
//        tree.add(7);
//        tree.add(8);
//        tree.add(9);
//        tree.add(6);
//        tree.add(5);
//        tree.add(4);
//        tree.add(10);
//        tree.add(2);
//        tree.add(1);
//
//        System.out.println(tree.toString());
//    }
}