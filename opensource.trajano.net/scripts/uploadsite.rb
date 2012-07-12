require 'net/ssh'
require 'net/sftp'
Net::SSH.start('shell.sourceforge.net', ARGV[0], ARGV[1]) do |ssh|
	ssh.sftp.connect do |sftp|
  		sftp.put_file('../index.html', '/home/groups/t/tt/ttl/htdocs/index.html')
  		sftp.put_file('../site.xml', '/home/groups/t/tt/ttl/htdocs/site.xml')
		sftp.put_file('../web/site.xsl', '/home/groups/t/tt/ttl/htdocs/web/site.xsl')
		sftp.put_file('../web/site.css', '/home/groups/t/tt/ttl/htdocs/web/site.css')
	end
end
