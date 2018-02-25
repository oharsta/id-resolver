cd ..
git clone git@gitlab.surfnet.nl:oharsta/id-resolver-environment.git
cd id-resolver/ansible
ansible-galaxy install geerlingguy.java
ansible-galaxy install geerlingguy.apache

ansible-playbook -i inventory/lab -K id-resolver.yml --tags "id-resolver-gui,id-resolver-server" -u development --extra-var="secrets_file=../../id-resolver-environment/secrets.yml"

ssh -4 development@idresolver.lab.surf.nl
cd /var/log/id-resolver
tail -500f id-resolver.log