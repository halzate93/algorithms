#include <iostream>
#include <vector>
#include <string>

using namespace std;

class Employee{
private:
  int id;
  Employee *min, *max;
  Employee *sup = NULL;
  vector<Employee*> sub;
  int pay;
  int minV, maxV;

public:
  Employee(int id){
    this->id = id;
    min = max = this;
    sub.push_back(this);
  }

  void updateSup(Employee* emp){
    bool changed = false;

    if(emp == min){
      searchMin();
      changed = true;
    }

    if(emp == max){
      maxV = max->getMax();
      changed = true;
    }

    if(emp->getMax() > max->getMax()){
      max = emp;
      maxV = max->getMax();
      changed = true;
    }

    if(emp->getMin() < min->getMin()){
      min = emp;
      minV = min->getMin();
      changed = true;
    }
    
    if(changed) update();
  }
  
  void update(){
    if(sup != NULL)
      sup->updateSup(this);
  }
  
  void setPay(int pay){
    this->pay = minV = maxV = pay;
    update();
  }

  void raisePay(int raise){
    pay+=raise;
    minV += raise;
    maxV += raise;
    for(int i=0; i<sub.size(); i++){
      if(sub[i] != this)
	sub[i]->raisePay(raise);
    }
  }

  void addSub(Employee* emp){
    sub.push_back(emp);
  }

  void searchMin(){
    Employee* newMin = sub[0];
    for(int i=1; i<sub.size(); i++){
      if(sub[i]->getMin() < newMin->getMin())
	newMin = sub[i];
    }
    min = newMin;
    minV = min->getMin();
  }

  int getId(){
    return id;
  }

  int getMin(){
    return minV;
  }

  int getMax(){
    return maxV;
  }
  
  int getPay(){
    return pay;
  }

  Employee* getSup(){
    return sup;
  }

  void setSup(Employee* sup){
    this->sup = sup;
  }

};

int main(){
  int t; cin >> t;
  while(t--){
    
    int n; cin >> n;
    Employee* list[n];
    
    list[0] = new Employee(1);

    for(int i=1; i<n; i++){
      int supId; cin >> supId;
      list[i] = new Employee(i+1);
      list[i]->setSup(list[supId-1]);
      list[supId-1]->addSub(list[i]);
    }

    for(int i = 0; i<n; i++){
      int pay; cin >> pay;
      list[i]->setPay(pay);
    }

    for(int i=0; i<n; i++){
      cout << list[i] << " ";
      cout << list[i]->getId() << " ";
      if(list[i]->getSup() != NULL)
	cout << "b:" << list[i]->getSup()->getId() << " ";
      cout << "p:" << list[i]->getPay() << " ";
      cout << "i:" << list[i]->getMax() - list[i]->getMin() << endl;
    }
    
    int q; cin >> q;
    char c; int id;
    while(q--){
      cin >> c; cin >> id;
      if(c == 'Q'){
	cout << list[id-1]->getMax() - list[id-1]->getMin() << endl;
      }else{
	int r; cin >> r;
	list[id-1]->raisePay(r);
	list[id-1]->update();
      }
    }
    
  }
}
