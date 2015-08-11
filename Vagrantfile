#
#   Copyright (C) 2005 - 2014 Jaspersoft Corporation. All rights  reserved.
#   http://www.jaspersoft.com.
#
#   Unless you have purchased  a commercial license agreement from Jaspersoft,
#   the following license terms  apply:
#
#   This program is free software: you can redistribute it and/or  modify
#   it under the terms of the GNU Affero General Public License  as
#   published by the Free Software Foundation, either version 3 of  the
#   License, or (at your option) any later version.
#
#   This program is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
#   GNU Affero  General Public License for more details.
#
#   You should have received a copy of the GNU Affero General Public  License
#   along with this program.&nbsp; If not, see <http://www.gnu.org/licenses/>.
#

Vagrant.configure(2) do |config|

	config.vm.box = "JasperSoft/JasperServer6.1.0"
	config.vm.box_check_update = true
	config.ssh.pty = true

	config.vm.network "forwarded_port", guest: 8080, host: 8090
	config.vm.network "forwarded_port", guest: 5432, host: 5430

	config.vm.provision "shell", inline: "/bin/sh /home/vagrant/jrs/ctlscript.sh start"

end
