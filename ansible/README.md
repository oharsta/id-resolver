ansible-galaxy install geerlingguy.java
ansible-galaxy install geerlingguy.apache

ansible-playbook -i inventory/lab -K id-resolver.yml --tags "id-resolver-gui,id-resolver-server" -u development --extra-var="secrets_file=../../id-resolver-environment/secrets.yml"