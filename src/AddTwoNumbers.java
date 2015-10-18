/**
 * <pre>
 *     You are given two linked lists representing two non-negative numbers.
 *     The digits are stored in reverse order and each of their nodes contain a single digit.
 *     Add the two numbers and return it as a linked list.
 *
 *     Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 *     Output: 7 -> 0 -> 8
 * </pre>
 *
 * Created by lingshu on 15/10/17.
 */
public class AddTwoNumbers {

    static class ListNode{
        private int val;
        private ListNode next;
        public ListNode(int val){
            this.val = val;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder("[");
            sb.append(val);
            ListNode node = next;
            while(node != null){
                sb.append(",").append(node.val);
                node = node.next;
            }
            if (sb.charAt(sb.length() - 1) == ','){
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            return sb.toString();
        }

        public ListNode appendNode(int value){
            next = new ListNode(value);
            return next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        // 判断第一个值是否需要进位
        boolean needCarry = false;
        // 记录当前节点的位置
        ListNode currentNode = root;
        ListNode preNode = root;
        // 在两个列表的next都不为空的情况下，将遍历到的value相加
        while (l1 != null && l2 != null){
            int currentVal = currentNode.val + l1.val + l2.val;
            // 当前两个元素相加之后是否需要进位
            needCarry = currentVal > 9;
            currentNode.val = currentVal % 10;
            l1 = l1.next;
            l2 = l2.next;
            // 每一次赋值都会增加一个next节点（while退出时，会导致多一个节点）
            currentNode.next = new ListNode(needCarry ? 1 : 0);
            preNode = currentNode;
            currentNode = currentNode.next;
        }
        // 第二个列表比较短
        while (l1 != null){
            currentNode.val += l1.val;
            needCarry = currentNode.val > 9;
            currentNode.val = currentNode.val % 10;
            currentNode.next = new ListNode(needCarry ? 1 : 0);
            preNode = currentNode;
            currentNode = currentNode.next;
            l1 = l1.next;
        }
        // 第一个列表比较短
        while (l2 != null){
            currentNode.val += l2.val;
            needCarry = currentNode.val > 9;
            currentNode.val = currentNode.val % 10;
            currentNode.next = new ListNode(needCarry ? 1 : 0);
            preNode = currentNode;
            currentNode = currentNode.next;
            l2 = l2.next;
        }
        // 最后一个节点是否是剩余的
        boolean leftElement = !needCarry;
        // 将最后剩余的一个node置成null
        if (leftElement){
            preNode.next = null;
        }
        return root;
    }

    public static void main(String[] args){
        ListNode l1 = new ListNode(5);
        System.out.println("l1 : " + l1);
        ListNode l2 = new ListNode(5);
        l2.appendNode(9).appendNode(1);
        System.out.println("l2 : " + l2);
        System.out.println("result : " + new AddTwoNumbers().addTwoNumbers(l1, l2).toString());
    }
}
