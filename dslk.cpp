#include<iostream>
using namespace std;

struct node{// dinh nghia 1 node
	int data;
	node *next;
};
struct dslk{ // dinh nghia 1 dslk don 
	node *head;
	node *tail;
    int size;
};
void init(dslk &a){// khoi tao 1 dslk 
	a.head = NULL;
	a.tail = NULL;
    a.size=0;

}
node *CreateNode(int x){
	node *tmp = new node;
	tmp->data = x;
	tmp->next = NULL;
	return tmp;
}
void addFirst(dslk &a, int x){
	node *newnode = CreateNode(x);
	if(a.head == NULL && a.tail == NULL){
		a.head = newnode;
		a.tail = newnode;
	}else{
		newnode->next = a.head;
		a.head = newnode;
	}
    a.size++;
}
void Traverse(dslk a){
	node *cur = a.head;
	while(cur != NULL){
		cout<<cur->data<<' ';
		cur = cur->next;
	}
}
void addLast(dslk &a, int x) {
    node *newnode = CreateNode(x);
    if (a.head == NULL && a.tail == NULL) {
        a.head = newnode;
        a.tail = newnode;
    } else {
        a.tail->next = newnode;
        a.tail = newnode;
    }
    a.size++;
}
void addBeforek(dslk &a, int x, int k){
    if (k<0 || k>a.size ) return;
    if(k==0) addFirst(a,x);
    else if (k==a.size) addLast(a,x);
    else {
        node *newnode = CreateNode(x);
        int idx=0;
        node *cur= a.head;
        node *lastcur;
        while (cur != NULL && idx < k-1){
            lastcur=  cur;
            cur = cur-> next;
            ++idx;
            if (idx==k-1){
                lastcur->next=newnode;
                newnode->next= cur;

            }
        }
        a.size++;
    }
}
void addAfterk(dslk &a, int x, int k){
    if (k<0 || k>a.size ) return;
    if(k==0) addFirst(a,x);
    else if (k==a.size) addLast(a,x);
    else {
        addBeforek(a,x,k+1);
        a.size++;
    }
}
void deleteFirst(dslk &a) {
    node *temp = a.head;
    a.head = a.head->next;
    delete temp;
    a.size--;
}
void deleteLast(dslk &a) {
    node *temp = a.head;
    a.head = a.head->next;
    delete temp;
    a.size--;
}



int main(){
	dslk a;
	init(a);
	addFirst(a,5);
	addFirst(a,4);
	addFirst(a,3);
	addFirst(a,2);
	addFirst(a,1);
    addLast(a,2);
	Traverse(a);
}
