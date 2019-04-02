Antonio Dan Macovei, 324CA

Data începerii temei: 17.11.2018
Data finalizării temei: 20.11.2018

Implementarea sistemului de VCS:

VCS-ul este initializat cu un snapshot al sistemului de fisiere, un prim
branch "master" si cu un prim commit "First commit". HEAD-ul este setat pe
acest prim commit si apoi se incepe citirea comenzilor. Tot in clasa VCS
se pastreaza o lista cu toate branch-urile si o variabila cu HEAD-ul curent.
Logica fiecarei comenzi de VCS se afla intr-o clasa separata. De asemenea
exista o clasa pentru Commit, in care avem mesajul, ID-ul, commit-ul parinte,
snapshot-ul sistemului de fisiere si branchul de care apartine.
Clasa Branch contine numele branch-ului si o lista de commituri.
Clasa Head este de tip Singleton, fiind nevoie de o singura instanta a ei care
sa pointeze catre commit-ul curent.
Clasa Staging este si ea un Singleton, intrucat vrem sa urmarim un singur
obiect in care avem o lista de FileSystemOperations.

Comenzile au fost implementate dupa cum urmeaza:

CommitOperation - verifica daca exista modificari in staging, verifica
daca parametrii sunt corecti, construieste mesajul din argumentele de intrare
creeaza un nou commit, il adauga la branch-ul curent si muta HEAD-ul pe el.

BranchOperation - se creeaza un nou branch cu numele dorit, se verifica daca
exista deja unul cu acelasi nume, se creeaza un nou commit cu aceleasi
proprietati si se adauga branch-ului nou creat, apoi branch-ul este adaugat 
in VCS.

CheckoutOperation - se verifica ce tip de checkout este (branch sau commit) si
se apeleaza functia corespunzatoare. Daca este checkout branch, se cauta in
lista de branch-uri cel dorit, se ia ultimul commit si se muta HEAD-ul pe el.
Daca este checkout commit, se cauta commit-ul dorit, se sterg commit-urile
efectuate dupa, se muta HEAD-ul pe cel gasit si se actualizeaza snapshot-ul
sistemului de fisiere.

RollbackOperation - sterge toate modificarile din staging si modifica
snapshot-ul sistemului de fisiere la cel din ultimul commit.

StatusOperation - se iau toate modificarile din staging si se afiseaza alaturi
de un text corespunzator.

LogOperation - se afiseaza toate commit-urile de pe branch-ul curent.
