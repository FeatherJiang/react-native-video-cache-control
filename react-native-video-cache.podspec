require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-video-cache-control"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-video-cache-control
                   DESC
  s.homepage     = "https://github.com/FeatherJiang/react-native-video-cache-control"
  s.license      = "MIT"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "feather" => "feather.jiang@icloud.com" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/FeatherJiang/react-native-video-cache-control.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency 'KTVHTTPCache', '~> 2.0.0'
  # ...
  # s.dependency "..."
end

