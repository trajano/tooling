require 'fileutils'
require 'net/ftp'

if ARGV[0] != nil
	ftp = Net::FTP.new('upload.sourceforge.net')
	ftp.login

	version = ARGV[0]
	Dir.foreach("../plugins") do |file_name|
		if file_name =~ /^net.trajano.*_#{version}.jar/
			ftp.putbinaryfile("../plugins/#{file_name}", "/incoming/#{file_name}")
			print <<EOT
<archive path="plugins/#{file_name}" 
  url="http://osdn.dl.sourceforge.net/sourceforge/ttl/#{file_name}"/>
EOT
		end
		if file_name =~ /^net.trajano.*_#{version}.zip/
			ftp.putbinaryfile("../plugins/#{file_name}", "/incoming/#{file_name}")
		end	
	end
end

